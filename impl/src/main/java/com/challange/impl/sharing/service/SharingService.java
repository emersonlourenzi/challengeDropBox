package com.challange.impl.sharing.service;

import com.challange.impl.sharing.repository.SharingEntity;
import com.challange.impl.sharing.repository.SharingRepository;
import com.challange.impl.upload.service.UploadService;
import com.mongodb.MongoClient;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class SharingService {
    private SharingRepository sr;
    private UploadService us;
    private final MongoClient mongoClient = new MongoClient();
    private final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "bancodesafio");

    public Page<SharingEntity> listAllByUser(String idUserCreator, Pageable pageable) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("idUserCreator").is(idUserCreator));
        List<SharingEntity> shares = mongoTemplate.find(query, SharingEntity.class);
        Page<SharingEntity> sharesPaged = pagedSearch(pageable, shares);
        //sr.findByIdUserCreator(id);
        return sharesPaged;
    }

    private Page<SharingEntity> pagedSearch(Pageable pageable, List<SharingEntity> shares) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<SharingEntity> list;

        if (shares.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, shares.size());
            list = shares.subList(startItem, toIndex);
        }

        Page<SharingEntity> sharesPage
                = new PageImpl<SharingEntity>(list, PageRequest.of(currentPage, pageSize), shares.size());

        return sharesPage;
    }

    public List<SharingEntity> listingAllSharing() {
        return sr.findAll();
    }

    public void deleteByFile(String nameFile) throws Exception {
        String idFile = us.fetchIdUpload(nameFile);
        if (!idFile.isEmpty()) {
            deleteByIdFile(idFile);
        } else {
            throw new Exception("Arquivo não encontrado.");
        }
    }

    public void deleteByIdFile(String id) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("idFile").is(id));
        mongoTemplate.remove(query, "shares");
    }
}
