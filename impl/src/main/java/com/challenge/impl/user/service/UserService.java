package com.challenge.impl.user.service;

import com.challenge.impl.user.model.UserModel;
import com.challenge.impl.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository repository;

    public List<UserModel> findAll() {
        return repository.findAll();
    }

    public Optional<UserModel> findById(String id) {
        return repository.findById(id);
    }

    public UserModel create(UserModel user) {
        return repository.save(user);
    }
}
