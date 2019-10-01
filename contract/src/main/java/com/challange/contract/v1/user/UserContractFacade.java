package com.challange.contract.v1.user;

import com.challange.contract.v1.user.mapper.UserMapper;
import com.challange.contract.v1.user.model.request.UserRequest;
import com.challange.contract.v1.user.model.response.UserResponse;
import com.challange.impl.user.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class UserContractFacade {

    private UserFacade facade;

    List<UserResponse> findAll() {
        return facade.findAll().stream()
                .map(UserMapper::mapToContract)
                .collect(Collectors.toList());
    }

    UserResponse findById(String id) throws Exception {
        return UserMapper.mapToContract(facade.findById(id));
    }

    UserResponse create(UserRequest user) {
        return UserMapper.mapToContract(facade.create(UserMapper.mapToImpl(user)));
    }

    void deleteById(String id) {
        facade.deleteById(id);
    }

    public UserResponse update(UserRequest user) {
        return UserMapper.mapToContract(facade.update(UserMapper.mapToImpl(user)));
    }
}
