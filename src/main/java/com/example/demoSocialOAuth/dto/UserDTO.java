package com.example.demoSocialOAuth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    String userId;
    String userName;
    String email;
    String password;
    String userImageUrl;
    String address;
    long phoneNo;

}
