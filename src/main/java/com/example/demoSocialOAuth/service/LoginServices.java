package com.example.demoSocialOAuth.service;


import com.example.demoSocialOAuth.entity.LoginTable;
import com.example.demoSocialOAuth.entity.UserEntity;

import java.util.List;

public interface LoginServices {

    UserEntity save(UserEntity userEntity);

    UserEntity findByEmail(UserEntity userEntity);

    List<LoginTable> getLoginLog(String userId);


}
