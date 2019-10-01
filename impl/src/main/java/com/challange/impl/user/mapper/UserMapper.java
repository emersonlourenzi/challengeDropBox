package com.challange.impl.user.mapper;

import com.challange.impl.user.repository.UserEntity;
import com.challange.impl.user.model.UserModel;

public class UserMapper {
    public static UserModel mapToModel(UserEntity userEntity) {
        return UserModel.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .build();
    }

    public static UserEntity mapToEntity(UserModel userModel) {
        return UserEntity.builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .email(userModel.getEmail())
                .build();
    }
}


