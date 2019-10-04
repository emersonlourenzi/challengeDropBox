package com.challange.impl.upload.service;

import com.challange.impl.sharing.service.SharingService;
import com.challange.impl.upload.repository.UploadEntity;
import com.challange.impl.upload.repository.UploadRepository;
import com.mongodb.MongoClient;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class UploadService {
    private UploadRepository ur;
    private SharingService ss;

    private final MongoClient mongoClient = new MongoClient();
    private final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient,"bancodesafio");

    public void listAllUploads() {
        ur.findAll();

    }

    public void save(UploadEntity up) {
        ur.save(up);
    }

    public void delete(String nameFile) throws Exception {
        String id = fetchIdUpload(nameFile);
        if(!id.isEmpty()) {
            ur.deleteById(id);
            ss.deleteByIdFile(id);
        }
        else {
            throw new Exception("Arquivo não encontrado");
        }
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

    public boolean verifyExists(String idFile) {
        return ur.existsById(idFile);
    }

    public void deleteByIdUser(String idUserCreator) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("idUserCreator").is(idUserCreator));
        List<UploadEntity> lisUpload = mongoTemplate.find(query, UploadEntity.class);
        for (Iterator iterator = lisUpload.iterator(); iterator.hasNext();) {
            UploadEntity upload = (UploadEntity) iterator.next();
            ss.deleteByIdFile(upload.getId());
            mongoTemplate.remove(upload);
        }
    }
}
