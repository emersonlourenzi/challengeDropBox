package com.challenge.impl.user;

import com.challenge.impl.user.model.UserModel;
import com.challenge.impl.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class UserFacade {

    private UserService service;

    public List<UserModel> findAll() {
        return service.findAll();
    }

    public Optional<UserModel> findById(String id) {
        return service.findById(id);
    }

    public UserModel create(UserModel user) {
        return service.create(user);
    }
}
