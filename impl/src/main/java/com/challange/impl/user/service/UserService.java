package com.challange.impl.user.service;

import com.challange.impl.user.mapper.UserMapper;
import com.challange.impl.user.model.UserModel;
import com.challange.impl.user.repository.UserEntity;
import com.challange.impl.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

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

    public UserModel create(UserModel user) {

        return UserMapper.mapToModel(repository.save(UserMapper.mapToEntity(user)));
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public UserModel update(UserModel user) {
        return UserMapper.mapToModel(repository.save(UserMapper.mapToEntity(user)));
    }

    public boolean verifyExists(String idUser) {
        return repository.existsById(idUser);
    }
}
