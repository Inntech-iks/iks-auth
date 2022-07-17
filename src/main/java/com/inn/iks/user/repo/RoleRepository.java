package com.inn.iks.user.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.iks.user.entity.Role;
import com.inn.iks.user.entity.User;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);
}
