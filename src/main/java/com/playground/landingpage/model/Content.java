package com.playground.landingpage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document("content")
public class Content {

    @Id
    private String id;
    private String name;
    private String creator;
    private Instant created;

    @Field(name = "content-type")
    @JsonProperty(value = "content-type")
    private String contentType;
    private String text;

    public Content(String id, String name, String creator,Instant created, String contentType, String text) {
        super();
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.created = created;
        this.contentType = contentType;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }
    @JsonProperty(value = "content-type")
    public String getContentType() {
        return contentType;
    }

    public String getText() {
        return text;
    }
    public Instant getCreated(){
        return created;
    }
}
