package com.example.demoSocialOAuth.service;

import com.example.demoSocialOAuth.entity.UserEntity;

public interface GoogleService {

    UserEntity getGmailDetails(String accessToken);

}
