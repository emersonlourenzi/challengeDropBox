package com.challange.contract.v1.user.mapper;

import com.challange.contract.v1.user.model.response.UserResponse;
import com.challange.contract.v1.user.model.request.UserRequest;
import com.challange.impl.user.model.UserModel;

public class UserMapper {

    public static UserResponse mapToContract(UserModel userModel) {
        return UserResponse.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .email(userModel.getEmail())
                .build();
    }

    public static UserModel mapToImpl(UserRequest userRequest) {
        return UserModel.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .pass(userRequest.getPass())
                .build();
    }
}
