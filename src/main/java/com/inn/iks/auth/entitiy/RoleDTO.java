package com.inn.iks.auth.entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RoleDTO {

    private String id;
    private String name;
    private String description;
    private String role;

}
