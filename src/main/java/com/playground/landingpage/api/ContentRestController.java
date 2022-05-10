package com.playground.landingpage.api;

import com.mongodb.client.result.UpdateResult;
import com.playground.landingpage.model.Content;
import com.playground.landingpage.model.ContentList;
import com.playground.landingpage.repo.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/content")
public class ContentRestController {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    MongoOperations mongoOperations;

    @GetMapping(produces = "application/json")
    public ContentList getContent(){
        ContentList contentList =new ContentList();
        contentList.setContent(contentRepository.findAll());
        return contentList;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Content postContent(@RequestBody Content content){
        return contentRepository.save(new Content(UUID.randomUUID().toString(), content.getName(),content.getCreator(), Instant.now(),content.getContentType(),content.getText()));
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Content partialUpdateContent(@RequestBody Content content){
        Query query = new Query(new Criteria("name").is(content.getName()));
        Update update = new Update();

        if(!Objects.isNull(content.getContentType())){
            update.set("content-type",content.getContentType());
        }
        if(!Objects.isNull(content.getCreator())){
            update.set("creator",content.getCreator());
        }
        if(!Objects.isNull(content.getText())){
            update.set("text",content.getText());
        }

        mongoOperations.updateFirst(query, update, Content.class);

        return contentRepository.findItemByName(content.getName());
    }

    @GetMapping(value="/reset",produces = "application/json")
    public ContentList initialized(){
        List<Content> contents =new ArrayList<>();
        contents.add(new Content(UUID.randomUUID().toString(),"intro","user1",Instant.now(),"text/plain","Introduction of content"));
        contents.add(new Content(UUID.randomUUID().toString(),"motd","user1",Instant.now(),"text/markdown","Mark down content for the intro"));

        ContentList contentList =new ContentList();
        contentList.setContent(contentRepository.saveAll(contents));
       return contentList;
    }

}
