package com.example.demoSocialOAuth.repository;

import com.example.demoSocialOAuth.entity.LoginTable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginTableRepository extends CrudRepository<LoginTable,Integer> {

    List<LoginTable> findByUserId(String userId);
}
