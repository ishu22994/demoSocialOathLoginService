package com.example.demoSocialOAuth.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document(collection = "userData")

public class UserEntity {

    @Id
    String userId;
    String userName;
    String email;
    String password;
    String userImageUrl;
    String address;
    long phoneNo;

}
