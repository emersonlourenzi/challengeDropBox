package com.challange.contract.v1.user.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Builder
@Document(value = "users")
public class UserRequest {
    private String id;
    private String name;
    private String email;
    private String pass;
}
