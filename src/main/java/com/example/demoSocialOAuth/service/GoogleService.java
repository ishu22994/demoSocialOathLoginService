package com.example.demoSocialOAuth.service;

import com.example.demoSocialOAuth.entity.UserEntity;
import org.springframework.social.google.api.plus.Person;

public interface GoogleService {

    /*String googleLogin();

    String getGoogleAccessToken(String code);

    Person getGoogleUserProfile(String accessToken);*/

    UserEntity getGmailDetails(String accessToken);

}
