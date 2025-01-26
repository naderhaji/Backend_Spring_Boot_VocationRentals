package com.hajji.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hajji.model.User;
import com.hajji.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {


    @Autowired
    private UserRepository userRepository;
    

    @Override
    public User findUserById(long userId) throws Exception {
        Optional<User> opt = userRepository.findById(userId);

        if(opt.isPresent()) {
            return opt.get();
        } else {
            throw new Exception("User not found with id "+ userId);
        }
    }

}
