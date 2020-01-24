package com.example.demoSocialOAuth.service;

import com.example.demoSocialOAuth.entity.UserEntity;
import com.example.demoSocialOAuth.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServicesImpl implements LoginServices {

    @Autowired
    LoginRepository loginRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return loginRepository.save(userEntity);
    }

    @Override
    public UserEntity findByEmail(UserEntity userEntity) {
        UserEntity user =  loginRepository.findByEmail(userEntity.getEmail());
        String pass1 = String.valueOf(user.getPassword().hashCode());
        String pass2 = String.valueOf(userEntity.getPassword().hashCode());
        if(pass1.equals(pass2) && user != null){
            return user;
        }
        return  user;
    }



}
