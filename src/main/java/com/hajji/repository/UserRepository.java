package com.hajji.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hajji.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

}
