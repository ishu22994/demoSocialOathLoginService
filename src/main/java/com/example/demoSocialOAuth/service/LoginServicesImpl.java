package com.example.demoSocialOAuth.service;

import com.example.demoSocialOAuth.entity.LoginTable;
import com.example.demoSocialOAuth.entity.UserEntity;
import com.example.demoSocialOAuth.repository.LoginRepository;
import com.example.demoSocialOAuth.repository.LoginTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServicesImpl implements LoginServices {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    LoginTableRepository loginTableRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return loginRepository.save(userEntity);
    }

    @Override
    public UserEntity findByEmail(UserEntity userEntity) {
        UserEntity user = loginRepository.findByEmail(userEntity.getEmail());
        String pass1 = String.valueOf(user.getPassword().hashCode());
        String pass2 = String.valueOf(userEntity.getPassword().hashCode());
        if (pass1.equals(pass2) && user != null) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public List<LoginTable> getLoginLog(String userId) {
        return loginTableRepository.findByUserId(userId);
    }


}
