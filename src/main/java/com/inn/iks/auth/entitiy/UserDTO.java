package com.inn.iks.auth.entitiy;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {

    private String id;
    private String username;
    
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
 
    @JsonProperty( value = "newPassword", access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;
    private String email;
    
    
    @Builder.Default
    private boolean isDeleted = false;
    @Builder.Default
    private boolean isDisabled = false; 

    private Collection<String> roles;
    
    public UserDTO(String username, String password) {
    	this.username = username;
    	this.password = password;
    }

}
