package com.inn.iks.auth.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.iks.auth.entitiy.AuthRequestDTO;
import com.inn.iks.auth.entitiy.AuthResponseDTO;
import com.inn.iks.auth.entitiy.UserDTO;
import com.inn.iks.auth.exception.AuthServiceException;
import com.inn.iks.auth.service.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/users/{pageSize}/{page}")
    public ResponseEntity<List<UserDTO>> getUsers(@PathVariable(name = "pageSize") int pageSize, @PathVariable(name = "page") int page) throws AuthServiceException  {
    	return ResponseEntity.ok(authService.getUsers(page,pageSize));
    }
    
    @GetMapping(value = "/user/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "username") String username) throws AuthServiceException  {
    	return ResponseEntity.ok(authService.getUser(username));
    }
    
    @GetMapping(value = "/user/{username}/{action}")
    public ResponseEntity<UserDTO> doAction(@RequestHeader HttpHeaders headers,@PathVariable(name = "username") String username, @PathVariable(name = "action") String action) throws AuthServiceException  {
    	return ResponseEntity.ok(authService.updateStatus(headers.get("username").get(0),username, action));
    }
    
    @PostMapping(value = "/token")
    public ResponseEntity<AuthResponseDTO> token(@RequestBody AuthRequestDTO authRequest) throws AuthServiceException  {
    	return ResponseEntity.ok(authService.token(authRequest));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserDTO> register(@RequestHeader HttpHeaders headers, @RequestBody UserDTO userDTO) throws AuthServiceException {
    	UserDTO resUser = authService.register(headers.get("username").get(0), userDTO);
    	return ResponseEntity.ok(resUser);
    }
    
    @PostMapping(value = "/changePassword")
    public ResponseEntity<UserDTO> changePassword(@RequestHeader HttpHeaders headers, @RequestBody UserDTO userDTO) throws AuthServiceException {
    	UserDTO resUser = authService.changePassword(headers.get("username").get(0), userDTO);
    	return ResponseEntity.ok(resUser);
    }
    
    @PostMapping(value = "/update")
    public ResponseEntity<UserDTO> updateUser(@RequestHeader HttpHeaders headers, @RequestBody UserDTO userDTO) throws AuthServiceException {
    	UserDTO resUser = authService.updateUser(headers.get("username").get(0), userDTO);
    	return ResponseEntity.ok(resUser);
    }
}
