package com.hajji.model;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class User {
    private Long id;
    private String password;
    private String email;
    private String fullName;
    
}