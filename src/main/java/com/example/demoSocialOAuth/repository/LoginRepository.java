package com.example.demoSocialOAuth.repository;


import com.example.demoSocialOAuth.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends MongoRepository<UserEntity,String> {

       UserEntity findByEmail(String email);

    String findByPassword(String email);
}
