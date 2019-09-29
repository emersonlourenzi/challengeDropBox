package com.challenge.contract.v1.user.mapper;

import com.challenge.contract.v1.user.model.response.UserResponse;
import com.challenge.contract.v1.user.model.request.UserRequest;
import com.challenge.impl.user.model.UserModel;

public class UserMapper {

    public static UserResponse mapToContract(UserModel userModel) {
        return UserResponse.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .email(userModel.getEmail())
                .pass(userModel.getPass())
                .build();
    }

    public static UserModel mapToImpl(UserRequest userRequest) {
        return UserModel.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();
    }
}
