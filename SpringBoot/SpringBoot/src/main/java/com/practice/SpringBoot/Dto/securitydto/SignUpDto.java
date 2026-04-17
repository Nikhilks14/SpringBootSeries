package com.practice.SpringBoot.Dto.securitydto;

import com.practice.SpringBoot.entity.enums.Permission;
import com.practice.SpringBoot.entity.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles; // Not recommended for testing
    private Set<Permission> permissions;
}
