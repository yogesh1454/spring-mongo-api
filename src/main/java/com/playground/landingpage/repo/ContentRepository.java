package com.playground.landingpage.repo;

import com.playground.landingpage.model.Content;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ContentRepository extends MongoRepository<Content,String> {

    @Query("{name:'?0'}")
    Content findItemByName(String name);


}
