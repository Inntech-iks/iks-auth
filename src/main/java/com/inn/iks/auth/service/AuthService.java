package com.inn.iks.auth.service;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.inn.iks.auth.entitiy.AuthRequestDTO;
import com.inn.iks.auth.entitiy.AuthResponseDTO;
import com.inn.iks.auth.entitiy.UserDTO;
import com.inn.iks.auth.exception.AuthServiceException;
import com.inn.iks.user.entity.Role;
import com.inn.iks.user.entity.User;
import com.inn.iks.user.service.RoleService;
import com.inn.iks.user.service.UserService;

@Service
public class AuthService {

    private final RoleService roleService;
    private final JwtUtil jwt;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthService(RoleService roleService,
                       final JwtUtil jwt, final UserService userService, final ModelMapper modelMapper) {
        this.jwt = jwt;
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void createUsers() {
//    	User user = userService.getById(1);
//    	if (user == null ) {
//        	userService.save(new User(1,"dunglt", BCrypt.hashpw("dunglt@123", BCrypt.gensalt()), "dunglt@gmail.com", false, false,Lists.newArrayList(new Role(1,"DSA","Nhân viên bán hàng"))));
//    	}
    }

/*
 * Get all users with pagable
 * Param: int page: query at page number
 * Param: int pageSize: number of records in a page.
 */
        public List<UserDTO> getUsers(int page, int pageSize)  {
        	Page<User> users = userService.getUsers(page, pageSize);
        	return users.stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        }    
/*
 * Get user by username
 * 
 */
    public UserDTO getUser(String username) throws AuthServiceException {
    	User user = userService.getByUsername(username);
    	if (user == null) {
    		throw new AuthServiceException("102", "User doesn't exist") ;
    	}
    	return modelMapper.map(user, UserDTO.class);
    }
/*
 * generate token 
 * 
 */
    public AuthResponseDTO token (AuthRequestDTO authRequest) throws AuthServiceException{
		/*
		 * Get username and password from request
		 * Query user from DB If user valid return token, Else return un-athorize
		 */
    	UserDTO userDTO = new UserDTO(authRequest.getUsername(),authRequest.getPassword());
    	User user = validateUser(userDTO, true);
    	
    	String accessToken = jwt.generate(modelMapper.map(user, UserDTO.class), "ACCESS");
        String refreshToken = jwt.generate(modelMapper.map(user, UserDTO.class), "REFRESH");
        return new AuthResponseDTO(accessToken, refreshToken);
    }

/*
 * Check if username exist, then return 
 * Check if Role is valid, then return
 * Create user with role
 */
    public UserDTO register(String requestor, UserDTO userDTO) throws AuthServiceException{
     	User aUser = userService.getByUsernameOrEmail(userDTO.getUsername(),userDTO.getEmail());
    	if (aUser != null ) {
    		//there should be return message to client
    		throw new AuthServiceException("101", "User exists in system, please check username and email") ;
    	}
    	Collection<String> strRoles = userDTO.getRoles() ;
    	if (strRoles == null || strRoles.isEmpty()) {
    		throw new AuthServiceException("105", "Roles object is not specified in request body") ;
    	}
    	
    	Collection<Role> roles = Lists.newArrayList();
    	boolean hasAtLeastValidRole = false; 
    	for (String strRole : strRoles) {
    		Role role = roleService.getByName(strRole) ; 
    		roles.add(role);
    		if (role != null) {
    			hasAtLeastValidRole = true;
    		}
		}
    	
    	if (!hasAtLeastValidRole ) {
    		throw new AuthServiceException("106", "Specified roles are not valid, at least one role must be existed in DB") ;
    	}
    	
    	aUser = modelMapper.map(userDTO, User.class);
    	aUser.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
    	aUser.setRoles(roles);
    	
    	/*
    	 * Set common fields: created by, created date, modified by, modified date
    	 */
    	aUser.setCreatedBy(requestor);
    	aUser.setModifiedBy(requestor);
    	
    	Date date = new Date();
    	aUser.setCreatedDate(date);
    	aUser.setModifiedDate(date);
    	
        return modelMapper.map(userService.save(aUser), UserDTO.class);

    }
    
   
	/*
	 * Check if username/password are correct
	 * Then update password
	 * @Param UserDTO: user need to be updated
	 */
    public UserDTO changePassword(String requestor, UserDTO userDTO) throws AuthServiceException {
    	User user = validateUser (userDTO, true);
    	user.setPassword(BCrypt.hashpw(userDTO.getNewPassword(), BCrypt.gensalt()));
    	
    	// Set common fields: created by, created date, modified by, modified date
    	user.setModifiedBy(requestor);
    	Date date = new Date();
    	user.setModifiedDate(date);
    	return modelMapper.map(userService.save(user), UserDTO.class);

    }
    /*
     * Update user info:
     *  - Check if user is validate
     *  - If request param is not null then set the param and save the user into DB
     *  - Return updated user 
     */
  
    public UserDTO updateUser (String requestor, UserDTO userDTO) throws AuthServiceException {
    	User user = validateUser (userDTO, false);
    	
    	if (userDTO.getEmail() != null) {
    		user.setEmail(userDTO.getEmail());
    	}
    	if (userDTO.getRoles() != null) {
        	Collection<Role> roles = Lists.newArrayList();
        	for (String strRole : userDTO.getRoles()) {
        		Role role = roleService.getByName(strRole) ; 
        		if (role != null) {
        			roles.add(role);
        		}
        	}
    		if (!roles.isEmpty()) {
    			user.setRoles(roles);
    		}
    	}
    	
    	if (userDTO.isDeleted() ) {
    		user.setDeleted(true);
    	}    	
    	if (userDTO.isDisabled()) {
    		user.setDisabled(true);
    	}
    	// Set common fields: created by, created date, modified by, modified date
    	user.setModifiedBy(requestor);
    	Date date = new Date();
    	user.setModifiedDate(date);
    	
    	return modelMapper.map(userService.save(user), UserDTO.class);
    }
    
    /*
     * doAction is used for is is_deleted = true/false ; is_disabled = true/false
     * 
     */
    public UserDTO updateStatus (String requestor, String username, String action) throws AuthServiceException {
    	User user = userService.getByUsername(username);
    	if (user == null ) {
    		throw new AuthServiceException("101", "Username or password is wrong") ;
    	}
    	switch (action) {
			case "disable": {
				user.setDisabled(true);
		    	
				// Set common fields: created by, created date, modified by, modified date
		    	user.setModifiedBy(requestor);
		    	Date date = new Date();
		    	user.setModifiedDate(date);				
				userService.save(user);
				break;
			}
			case "enable": {
				user.setDisabled(false);
				userService.save(user);
		    	
				// Set common fields: created by, created date, modified by, modified date
		    	user.setModifiedBy(requestor);
		    	Date date = new Date();
		    	user.setModifiedDate(date);				
				userService.save(user);
				
				break;				
			}
			case "delete": {
				user.setDeleted(true);
				userService.save(user);

				// Set common fields: created by, created date, modified by, modified date
		    	user.setModifiedBy(requestor);
		    	Date date = new Date();
		    	user.setModifiedDate(date);				
				userService.save(user);
				break;				
			}
			case "undelete": {
				user.setDeleted(false);
				userService.save(user);

				// Set common fields: created by, created date, modified by, modified date
		    	user.setModifiedBy(requestor);
		    	Date date = new Date();
		    	user.setModifiedDate(date);				
				userService.save(user);
				break;				
			}
			default:
				throw new AuthServiceException("201", "Action is not valid") ;

		}
	    return modelMapper.map(userService.save(user), UserDTO.class);
    }        
/*
 * =====================
 * ---Private methods
 * =====================    
 */
    /*
     * Validate if user exists, is not deleted, is not disabled. 
     * Return user if all conditions are true. Else throws exception
     * 
     */
    
    private User validateUser (UserDTO userDTO, boolean checkPassword)throws AuthServiceException {
    	User user = userService.getByUsername(userDTO.getUsername());
    	if (user == null ) {
    		throw new AuthServiceException("101", "Username or password is wrong") ;
    	}
    	if (checkPassword) {
        	//check password is correct
        	if (!BCrypt.checkpw(userDTO.getPassword(), user.getPassword())) {
        		throw new AuthServiceException("100", "Username or password is wrong") ;
        	} 		
    	}

    	if (user.isDeleted() ) {
    		throw new AuthServiceException("104", "User is deleted") ;
    	}
    	if (user.isDisabled()) {
    		throw new AuthServiceException("103", "User is disabled") ;
    	}    	
    	return user;
    }
    

   
}
