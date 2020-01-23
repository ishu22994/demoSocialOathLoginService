package com.example.demoSocialOAuth.service;

import com.example.demoSocialOAuth.entity.UserEntity;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;



@Service
public class GoogleServiceImpl implements GoogleService {

    @Autowired
    LoginServices loginServices;

    private static final String GOOGLE_APP_CLIENT_ID = "959388190902-n2h383o5ej00boqakorh0qn4iodcnd95.apps.googleusercontent.com";
    @Value(GOOGLE_APP_CLIENT_ID)
    private List<String> googleAppClientIdList;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private HttpTransport httpTransport;

    @PostConstruct
    public void init() throws GeneralSecurityException, IOException {
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    }
    public GoogleIdTokenVerifier getGoogleIdTokenVerifier() {
        return new GoogleIdTokenVerifier.Builder
                (httpTransport, JSON_FACTORY).setAudience(googleAppClientIdList).build();
    }

    @Override
    public UserEntity getGmailDetails(String accessToken) {
        System.out.println("Inside Gmail Details");
        UserEntity user = new UserEntity();
        try {
            GoogleIdToken verifyGoogleIdToken = getGoogleIdTokenVerifier().verify(accessToken);
            if (verifyGoogleIdToken != null) {
                System.out.println("Inside verifyGoogleIDToken");
                user.setEmail(verifyGoogleIdToken.getPayload().getEmail());
                System.out.println(verifyGoogleIdToken.getPayload().getEmail());
                loginServices.save(user);
            } else {
                System.out.println("Not valid Token");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}