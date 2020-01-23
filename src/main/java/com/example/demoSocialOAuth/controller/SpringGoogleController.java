/*
package com.example.demoSocialOAuth.controller;//package com.training.loginservice.controller;


import com.example.demoSocialOAuth.model.UserInfo;
import com.example.demoSocialOAuth.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.plus.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SpringGoogleController {

    @Autowired
    private GoogleService googleService;

    @GetMapping("/googleLogin")
    public RedirectView Login(){
        RedirectView redirectView = new RedirectView();
        String url = googleService.googleLogin();
        System.out.println(url);
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping("/google")
    public String google(@RequestParam("code") String code){
        String accessToken = googleService.getGoogleAccessToken(code);
        return googleprofiledata(accessToken);
    }

    //We have to hit this API....after getting access token
    @GetMapping("/googleLoginaccess")
    public String googleprofiledata(@RequestParam String accessToken){
        Person user= googleService.getGoogleUserProfile(accessToken);
        UserInfo userInfo=new UserInfo(user.getGivenName(),user.getImageUrl());
        //fetch all the details of user to our main database of user
        return userInfo.getEmail();
    }

}
*/
