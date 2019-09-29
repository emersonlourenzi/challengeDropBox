package com.challenge.impl.user.mapper;

import com.challenge.impl.user.repository.UserEntity;
import com.challenge.impl.user.model.UserModel;

public class UserMapper {
    public static UserModel mapToModel(UserEntity userEntity) {
        return UserModel.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .pass(userEntity.getPass())
                .build();
    }

    public static UserEntity mapToEntity(UserModel userModel) {
        return UserEntity.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .email(userModel.getEmail())
                .pass(userModel.getPass())
                .build();
    }
}


