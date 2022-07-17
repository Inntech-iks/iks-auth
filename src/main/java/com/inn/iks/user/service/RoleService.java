package com.inn.iks.user.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inn.iks.user.entity.Role;
import com.inn.iks.user.entity.User;
import com.inn.iks.user.repo.RoleRepository;
import com.inn.iks.user.repo.UserRepository;

@Service
@Slf4j
public class RoleService {

    private final RoleRepository roleRepo;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.roleRepo = repository;
    }

    public Role save(Role role) {
        return this.roleRepo.save(role);
    }

    public Role getById(long id) {
        return this.roleRepo.findById(id).orElse(null);
    }

    public Role getByName (String name) {
    	return roleRepo.findByName(name);
    }


}
