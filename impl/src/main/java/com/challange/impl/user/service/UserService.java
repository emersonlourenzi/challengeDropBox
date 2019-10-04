package com.challange.impl.user.service;

import com.challange.impl.file.service.FileService;
import com.challange.impl.user.mapper.UserMapper;
import com.challange.impl.user.model.UserModel;
import com.challange.impl.user.repository.UserEntity;
import com.challange.impl.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;
    private FileService fs;
    private FTPClient ft;

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
            throw new Exception("Usuário não encontrado.");
        }
    }

    public UserModel create(UserModel user) throws Exception {
        UserModel user1 = UserMapper.mapToModel(repository.save(UserMapper.mapToEntity(user)));
        fs.connectServerFTP();
        ft.makeDirectory(user1.getId());
        return user1;
    }

    public void deleteById(String id) throws Exception {
        if (fs.verifyExists(id)) {
            repository.deleteById(id);
            fs.connectServerFTP();
            ft.enterLocalPassiveMode();
            ft.changeWorkingDirectory(id);
            for (String x : ft.listNames()) {
                ft.deleteFile(x);
            }
            ft.changeToParentDirectory();
            ft.removeDirectory(id);
        } else {
            throw new Exception("Usuário inexistente.");
        }
    }

    public UserModel update(String id, UserModel user) {
        repository.deleteById(id);
        user.setId(id);
        return UserMapper.mapToModel(repository.save(UserMapper.mapToEntity(user)));
    }
}
