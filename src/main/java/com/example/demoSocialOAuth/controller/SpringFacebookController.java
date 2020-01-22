package com.example.demoSocialOAuth.controller;


import com.example.demoSocialOAuth.model.UserInfo;
import com.example.demoSocialOAuth.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SpringFacebookController {

    @Autowired
    private FacebookService facebookService;

    @GetMapping("/facebookLogin")
    public RedirectView facebookLogin(){
        RedirectView redirectView = new RedirectView();
        String url = facebookService.facebookLogin();
        System.out.println(url);
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping("/facebook")
    public String facebook(@RequestParam("code") String code){
        String accessToken = facebookService.getFacebookAccessToken(code);
        return facebookprofiledata(accessToken);
    }

    public String facebookprofiledata( String accessToken){
        User user= facebookService.getFacebookUserProfile(accessToken);
        UserInfo userInfo=new UserInfo(user.getFirstName(),user.getLastName(),user.getEmail());
        System.out.println(userInfo.getEmail());
        //fetch all the details of user to our main database of user
        return "My Url is:" + "http://localhost:8080";
    }

}
