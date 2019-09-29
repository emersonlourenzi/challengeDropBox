package com.challenge.contract.v1.user;

import com.challenge.contract.v1.user.mapper.UserMapper;
import com.challenge.contract.v1.user.model.request.UserRequest;
import com.challenge.contract.v1.user.model.response.UserResponse;
import com.challenge.impl.user.UserFacade;
import com.challenge.impl.user.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
class UserContractFacade {

    private UserFacade facade;

    List<UserModel> findAll() {
        return facade.findAll();
    }

    Optional<UserModel> findById(String id) {
        return facade.findById(id);
    }

    UserResponse create(UserRequest user) {
        return UserMapper.mapToContract(facade.create(UserMapper.mapToImpl(user)));
    }
}
