package com.example.demoSocialOAuth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDTO {

    String userId;
    String userName;
    String email;
    String password;
    String userImageUrl;
    String address;
    long phoneNo;

}
