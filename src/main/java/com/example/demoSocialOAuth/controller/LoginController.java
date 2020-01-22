package com.example.demoSocialOAuth.controller;//package com.training.loginservice.controller;



import com.example.demoSocialOAuth.config.JwtGenerator;
import com.example.demoSocialOAuth.dto.UserDTO;
import com.example.demoSocialOAuth.entity.UserEntity;
import com.example.demoSocialOAuth.service.LoginServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    LoginServices loginServices;

    @Autowired
    JwtGenerator jwtGenerator = new JwtGenerator();

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO)
    {
        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(userDTO,userEntity);
        UserEntity userCreated=loginServices.save(userEntity);
        return new ResponseEntity<String>(userCreated.getUserName(),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO){

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

}

