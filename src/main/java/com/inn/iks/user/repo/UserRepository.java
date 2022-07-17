package com.inn.iks.user.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.iks.user.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	User findByUsernameOrEmail(String username,String email);
	User findByUsernameAndPassword(String username,String password);
	Page<User> findByUsernameLike(String username, Pageable pageSetting);
}
