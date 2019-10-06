package com.challange.impl.user.service;

import com.challange.impl.file.service.FileService;
import com.challange.impl.upload.UploadFacade;
import com.challange.impl.user.mapper.UserMapper;
import com.challange.impl.user.model.UserModel;
import com.challange.impl.user.repository.UserEntity;
import com.challange.impl.user.repository.UserRepository;
import com.mongodb.MongoClient;
import lombok.AllArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UploadFacade uf;
    private UserRepository repository;
    private FileService fs;
    private FTPClient ft;
    private final MongoClient mongoClient = new MongoClient();
    private final MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "bancodesafio");

    public List<UserModel> findAll() {
        return repository.findAll().stream()
                .map(UserMapper::mapToModel)
                .collect(Collectors.toList());
    }

    public UserModel findById(String id) throws Exception {
        Optional<UserEntity> user = repository.findById(id);
        if (!user.isEmpty()) {
            return UserMapper.mapToModel(repository.findById(id).orElseThrow(InputMismatchException::new));
        } else {
            throw new Exception("User not found. ");
        }
    }

    public UserModel create(UserModel user) throws Exception {
        UserModel user1 = UserMapper.mapToModel(repository.save(UserMapper.mapToEntity(user)));
        fs.connectServerFTP();
        try {
            ft.makeDirectory(user1.getId());
            return user1;
        } catch (IOException e) {
            e.getMessage();
            return null;
        } finally {
            try {
                ft.logout();
                ft.disconnect();
            } catch (IOException e) {
                e.getMessage();
            }
        }

    }

    public void deleteById(String id) throws Exception {
        if (fs.verifyExists(id)) {
            repository.deleteById(id);
            uf.deleteByIdUser(id);
            fs.connectServerFTP();
            try {
                ft.enterLocalPassiveMode();
                ft.changeWorkingDirectory(id);
                for (String x : ft.listNames()) {
                    ft.deleteFile(x);
                }
                ft.changeToParentDirectory();
                ft.removeDirectory(id);
            } catch (IOException e) {
                e.getMessage();
            } finally {
                try {
                    ft.logout();
                    ft.disconnect();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        } else {
            throw new Exception("nonexistent user");
        }
    }

    public UserModel update(String id, UserModel user) {
        repository.deleteById(id);
        user.setId(id);
        return UserMapper.mapToModel(repository.save(UserMapper.mapToEntity(user)));
    }

    public boolean verifyExists(String id) {
        return repository.existsById(id);
    }

    public String fetchIdUser(String email) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        UserEntity ue = mongoTemplate.findOne(query, UserEntity.class);
        if (ue != null) {
            return ue.getId();
        } else {
            return "";
        }
    }

    public UserEntity fetchByName(String name) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("nome").is(name));
        UserEntity ue = mongoTemplate.findOne(query, UserEntity.class);
        return ue;
    }
}
