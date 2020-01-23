package com.example.demoSocialOAuth.controller;


import com.example.demoSocialOAuth.entity.UserEntity;
import com.example.demoSocialOAuth.model.UserInfo;
import com.example.demoSocialOAuth.repository.LoginRepository;
import com.example.demoSocialOAuth.service.FacebookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SpringFacebookController {

    @Autowired
    private FacebookService facebookService;

    @Autowired
    private LoginRepository loginRepository;


    @GetMapping("/facebookLogin")
    public String facebookLogin(){
       //RedirectView redirectView = new RedirectView();
        String url = facebookService.facebookLogin();
        System.out.println(url);
        //redirectView.setUrl(url);
        return url;
    }

    @GetMapping("/facebook")
    public String facebook(@RequestParam("code") String code){
        String accessToken = facebookService.getFacebookAccessToken(code);
        return facebookprofiledata(accessToken);
    }

    public String facebookprofiledata( String accessToken){
        User user= facebookService.getFacebookUserProfile(accessToken);
        UserInfo userInfo=new UserInfo(user.getFirstName(),user.getEmail());
        System.out.println(userInfo.getFirstName());
        System.out.println(userInfo.getEmail());

        UserEntity userExist = loginRepository.findByEmail(user.getEmail());
        if(userExist != null){
            return "User is already Exist";
        }else{
            UserEntity userEntity=new UserEntity();
            BeanUtils.copyProperties(userExist,userEntity);
            loginRepository.save(userEntity);
        }
        return "My Url is:" + "http://localhost:8080";
    }

}
