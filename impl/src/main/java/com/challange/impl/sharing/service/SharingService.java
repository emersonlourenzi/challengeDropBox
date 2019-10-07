package com.challange.impl.sharing.service;

import com.challange.impl.FacadeGeneral;
import com.challange.impl.sharing.repository.SharingEntity;
import com.challange.impl.sharing.repository.SharingRepository;
import com.challange.impl.upload.UploadFacade;
import com.challange.impl.user.UserFacade;
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
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class SharingService {
    private UploadFacade upf;
    private UserFacade uf;
    private SharingRepository sr;
    private FacadeGeneral fg;
    private final MongoClient mongoClient = new MongoClient();
    private final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "bancodesafio");

    public Page<SharingEntity> listAllByUser(Model model, String idUser, Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<SharingEntity> sharePaged = listAllByUser1(idUser, PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("sharePaged", sharePaged);

        int totPages = sharePaged.getTotalPages();
        if (totPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return sharePaged;
    }

    private Page<SharingEntity> listAllByUser1(String idUserCreator, Pageable pageable) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("idUserCreator").is(idUserCreator));
        List<SharingEntity> shares = mongoTemplate.find(query, SharingEntity.class);
        Page<SharingEntity> sharesPaged = pagedSearch(pageable, shares);
        //sr.findByIdUserCreator(idUserCreator);
        return sharesPaged;
    }

    public Page<SharingEntity> listSharedWithMe(Model model, String idUser, Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<SharingEntity> sharePaged = listSharedWithMe1(idUser, PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("sharePaged", sharePaged);

        int totPages = sharePaged.getTotalPages();
        if (totPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return sharePaged;
    }

    private Page<SharingEntity> listSharedWithMe1(String idUser, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("idUser").is(idUser));
        List<SharingEntity> shares = mongoTemplate.find(query, SharingEntity.class);
        Page<SharingEntity> sharesPaged = pagedSearch(pageable, shares);
        //sr.findByIdUserCreator(idUserCreator);
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
        String idFile = fg.fetchIdUpload(nameFile);
        if (!idFile.isEmpty()) {
            deleteByIdFile(idFile);
        } else {
            throw new Exception("File not found.");
        }
    }

    public void deleteByIdFile(String id) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("idFile").is(id));
        mongoTemplate.remove(query, "shares");
    }

    public void delete(String emailWithWhomYouShared, String nameFile, String idUser) throws Exception {
        String idFile = fg.fetchIdUpload(nameFile);
        if (!idFile.isEmpty()) {
            String idUserShared = uf.fetchByIdUser(emailWithWhomYouShared);
            if (!idUser.isEmpty()) {
                final Query query = new Query();
                query.addCriteria(Criteria.where("idFile").is(idFile).and("idUser").is(idUserShared).and("idUserCreator").is(idUser));
                SharingEntity up = mongoTemplate.findOne(query, SharingEntity.class);
                if (up != null) {
                    mongoTemplate.remove(query, "shares");
                } else {
                    throw new Exception("This file is not shared with this user.");
                }
            } else {
                throw new Exception("User not found.");
            }
        } else {
            throw new Exception("File not found.");
        }
    }

    public void share(String nameFile, String idUserShared, String emailUserForShare) throws Exception {
        String idFile = upf.fetchIdUpload(nameFile);
        if (upf.verifyExists(idFile)) {
            String idUser = uf.fetchByIdUser(emailUserForShare);
            if (uf.verifyExists(idUser)) {
                SharingEntity se = new SharingEntity(idFile, idUser, idUserShared);
                sr.save(se);
            } else {
                throw new Exception("Nonexistent user.");
            }
        } else {
            throw new Exception("The file you are trying to share does not exist.");
        }
    }

}
