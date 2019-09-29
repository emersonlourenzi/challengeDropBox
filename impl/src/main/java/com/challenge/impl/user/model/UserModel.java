package com.challenge.impl.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Builder
@Document(value = "users")
public class UserModel {
    private String id;
    private String name;
    private String email;
    private String pass;
}
