package com.challange.impl;

import com.challange.impl.upload.repository.UploadEntity;
import com.mongodb.MongoClient;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FacadeGeneral {
    private final MongoClient mongoClient = new MongoClient();
    private final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "bancodesafio");

    public void deleteByIdFile(String id) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("idFile").is(id));
        mongoTemplate.remove(query, "shares");
    }

    public String fetchIdUpload(String nameFile) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("nameFile").is(nameFile));
        UploadEntity upload = mongoTemplate.findOne(query, UploadEntity.class);
        if(upload != null) {
            return upload.getId();
        }
        else {
            return "";
        }
    }
}
