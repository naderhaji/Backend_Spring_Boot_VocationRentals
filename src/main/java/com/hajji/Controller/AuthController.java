package com.hajji.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import com.hajji.Request.LoginRequest;
import com.hajji.Response.AuthResponse;
import com.hajji.config.JwtProvider;
import com.hajji.model.User;
import com.hajji.repository.UserRepository;
import com.hajji.service.CustomerUserDetailsService;


@RestController
@RequestMapping("/auth")
public class AuthController {
    

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CustomerUserDetailsService costumerUserDetailsService;
    
    @Autowired
    private JwtProvider jwtProvider;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user) throws Exception {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullName = user.getFullName();
    
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
    
        User isExistEmail = userRepository.findByEmail(email);
        if (isExistEmail != null) {
            throw new Exception("Email is already used with another account");
        }
    
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFullName(fullName);
    
        User savedUser = userRepository.save(createdUser);
    
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        String token = jwtProvider.generateToken(authentication);
    
        AuthResponse res = new AuthResponse();
        res.setJwt(token);
        res.setMessage("signup success");
    
        return res;
    }

@PostMapping("/signin")
public AuthResponse signinHandler(@RequestBody LoginRequest loginRequest){

    String username = loginRequest.getEmail();
    String password = loginRequest.getPassword();

    Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        String Token = jwtProvider.generateToken(authentication);
    
        AuthResponse res = new AuthResponse();
    
        res.setJwt(Token);
        res.setMessage("signin success");
    
    
        return res;
    
    }
    
    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = costumerUserDetailsService.loadUserByUsername(username);

        if(userDetails== null){
            throw new BadCredentialsException("User not found with email "+username);
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Password is incorrect");
        }




        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }

}
