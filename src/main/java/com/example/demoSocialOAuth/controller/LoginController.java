package com.example.demoSocialOAuth.controller;
import com.example.demoSocialOAuth.config.JwtGenerator;
import com.example.demoSocialOAuth.dto.AccessTokenDto;
import com.example.demoSocialOAuth.dto.FacebookDTO;
import com.example.demoSocialOAuth.dto.UserDTO;
import com.example.demoSocialOAuth.entity.LoginTable;
import com.example.demoSocialOAuth.entity.UserEntity;
import com.example.demoSocialOAuth.repository.LoginRepository;
import com.example.demoSocialOAuth.repository.LoginTableRepository;
import com.example.demoSocialOAuth.service.GoogleService;
import com.example.demoSocialOAuth.service.LoginServices;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LoginController {

    @Autowired
    LoginServices loginServices;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    LoginTableRepository loginTableRepository;

    @Autowired
    JwtGenerator jwtGenerator = new JwtGenerator();

    @Autowired
    GoogleService googleService;


    @PostMapping("/register")
    public AccessTokenDto register(@RequestBody UserDTO userDTO)
    {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(userDTO,userEntity);
        UserEntity userCreated=loginServices.save(userEntity);
        accessTokenDto.setUserId(userCreated.getUserId());
        accessTokenDto.setEmail(userCreated.getEmail());
        return new AccessTokenDto();
    }

    @PostMapping("/login")
    public AccessTokenDto login(@RequestBody UserDTO userDTO){
        //System.out.println(userDTO.getEmail());
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(userDTO,userEntity);
        UserEntity userExist = loginServices.findByEmail(userEntity);
        if(userExist != null){
           String accessToken =  jwtGenerator.generateToken(userEntity);
           accessTokenDto.setAccessToken(accessToken);
           accessTokenDto.setUserId(userExist.getUserId());
           accessTokenDto.setEmail(userExist.getEmail());
           accessTokenDto.setCheck(true);
           LoginTable loginTable = new LoginTable();
           loginTable.setUserId(userExist.getUserId());
           loginTableRepository.save(loginTable);
           return accessTokenDto;
        }else{
            accessTokenDto.setCheck(false);
            return new AccessTokenDto();
        }

    }

    @GetMapping("/googlelogin/{idToken}")
    public AccessTokenDto googlelogin(@PathVariable("idToken")  String idToken) {
        System.out.println("Inside gmail login");
        UserEntity user=googleService.getGmailDetails(idToken);
        if(user!=null) {
            String Token = jwtGenerator.generateToken(user);
            AccessTokenDto accessTokenDto = new AccessTokenDto();
            accessTokenDto.setUserId(user.getUserId());
            accessTokenDto.setAccessToken(Token);
            accessTokenDto.setEmail(user.getEmail());
            LoginTable loginTable = new LoginTable();
            loginTable.setUserId(user.getUserId());
            loginTableRepository.save(loginTable);
            return accessTokenDto;
        }else
            return new AccessTokenDto();
    }

    @GetMapping("/facebooklogin/{accessToken}")
    public AccessTokenDto facebookLogin(@PathVariable("accessToken") String accessToken) {
        FacebookDTO userDTO=(new RestTemplate()).getForObject("https://graph.facebook.com/me?fields=name,id,email,first_name,last_name&access_token=" + accessToken , FacebookDTO.class);
        if(userDTO!=null){
            System.out.println(userDTO.getEmail());
            UserEntity user=new UserEntity();
            user.setEmail(userDTO.getEmail());
            user.setUserId(userDTO.getId());
            UserEntity user1 =  loginRepository.findByEmail(user.getEmail());
            if(user1 == null){
                loginServices.save(user);
            }
            String Token =  jwtGenerator.generateToken(user);
            String userId = user.getUserId();
            AccessTokenDto accessTokenDto=new AccessTokenDto();
            accessTokenDto.setUserId(userId);
            accessTokenDto.setAccessToken(Token);
            accessTokenDto.setEmail(user.getEmail());
            LoginTable loginTable = new LoginTable();
            loginTable.setUserId(user.getUserId());
            loginTableRepository.save(loginTable);
            return accessTokenDto;
        }
       return  new AccessTokenDto();
    }

    @GetMapping(value ="/loginLog/{userId}")
    public List<LoginTable> getLoginLog(@PathVariable("userId") String userId){
        System.out.println("Inside Logi log");
        return loginServices.getLoginLog(userId);
    }


}

