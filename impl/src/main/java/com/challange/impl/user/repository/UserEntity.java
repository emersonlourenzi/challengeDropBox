package com.challange.impl.user.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Builder
@Document(value = "users")
public class UserEntity {

    private String id;
    private String name;
    private String email;
    private String pass;
}
