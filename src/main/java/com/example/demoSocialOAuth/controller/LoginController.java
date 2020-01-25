package com.example.demoSocialOAuth.controller;
import com.example.demoSocialOAuth.config.JwtGenerator;
import com.example.demoSocialOAuth.dto.AccessTokenDto;
import com.example.demoSocialOAuth.dto.FacebookDTO;
import com.example.demoSocialOAuth.dto.UserDTO;
import com.example.demoSocialOAuth.entity.UserEntity;
import com.example.demoSocialOAuth.repository.LoginRepository;
import com.example.demoSocialOAuth.service.GoogleService;
import com.example.demoSocialOAuth.service.LoginServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LoginController {

    @Autowired
    LoginServices loginServices;

    @Autowired
    LoginRepository loginRepository;

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
           return accessTokenDto;
        }else{
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
            return accessTokenDto;
        }
       return  new AccessTokenDto();
    }


}

