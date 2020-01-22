package com.example.demoSocialOAuth.service;


import com.example.demoSocialOAuth.entity.UserEntity;

public interface LoginServices {

    UserEntity save(UserEntity userEntity);

    boolean findByEmail(UserEntity userEntity);
}
