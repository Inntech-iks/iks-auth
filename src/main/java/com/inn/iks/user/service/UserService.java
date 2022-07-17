package com.inn.iks.user.service;


import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inn.iks.user.entity.User;
import com.inn.iks.user.repo.UserRepository;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository repository) {
        this.userRepo = repository;
    }

    public User getById(long id) {
        return this.userRepo.findById(id).orElse(null);
    }

    public User getByUsername (String username) {
    	return userRepo.findByUsername(username);
    }

    public User getByUsernameOrEmail (String username, String email) {
    	return userRepo.findByUsernameOrEmail(username, email);
    }

    public User getByUsernameAndPassword (String username, String password) {
    	return userRepo.findByUsernameAndPassword(username, password);
    }
    
    public Page<User> getUsers (int page, int pageSize ) {
    	return userRepo.findAll(PageRequest.of(page, pageSize));
    }
    
    public Page<User> getUserLike(String username, int page, int pageSize){
    	return userRepo.findByUsernameLike(username, PageRequest.of(page, pageSize));
    }
    public User updateUser (User user) {
    	return userRepo.save(user);
    }
    public User save(User user) {
        return userRepo.save(user);
    }
}
