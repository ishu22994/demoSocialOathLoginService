package com.example.demoSocialOAuth.controller;//package com.training.loginservice.controller;


import com.example.demoSocialOAuth.config.JwtGenerator;
import com.example.demoSocialOAuth.dto.FacebookDTO;
import com.example.demoSocialOAuth.dto.UserDTO;
import com.example.demoSocialOAuth.entity.UserEntity;
import com.example.demoSocialOAuth.service.GoogleService;
import com.example.demoSocialOAuth.service.LoginServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LoginController {

    @Autowired
    LoginServices loginServices;

    @Autowired
    JwtGenerator jwtGenerator = new JwtGenerator();

    @Autowired
    GoogleService googleService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO)
    {
        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(userDTO,userEntity);
        UserEntity userCreated=loginServices.save(userEntity);
        return new ResponseEntity<String>(userCreated.getUserId(),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO){
        //System.out.println(userDTO.getEmail());
        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(userDTO,userEntity);
        boolean userExist = loginServices.findByEmail(userEntity);
        if(userExist){
           String accessToken =  jwtGenerator.generateToken(userEntity);
           return new ResponseEntity<String>(accessToken,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<String>("User is Invalid",HttpStatus.CREATED);
        }

    }

    @PostMapping("/googlelogin")
    public String gmailLogin(String accessToken) {
        System.out.println("Inside gmail login");
        UserEntity user=googleService.getGmailDetails(accessToken);
        if(user!=null)
            return accessToken;
        else
            return "User Not Found";
    }

    @PostMapping("/facebooklogin")
    public String facebookLogin(@RequestBody String accessToken) {
        FacebookDTO userDTO=(new RestTemplate()).getForObject("https://graph.facebook.com/me?fields=name,id,email,first_name,last_name&access_token=" + accessToken , FacebookDTO.class);
        if(userDTO!=null){
            System.out.println(userDTO.getEmail());
            UserEntity user=new UserEntity();
            user.setEmail(userDTO.getEmail());
            loginServices.save(user);
            return accessToken;
        }
        else
            return "User Not Found";
    }


}

