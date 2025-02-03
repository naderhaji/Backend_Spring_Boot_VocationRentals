package com.hajji.service;

import com.hajji.model.User;

public interface UserService {

    public User findUserById(long userId) throws Exception;

    public User findUserByJwt(String jwt) throws Exception;

    
}
