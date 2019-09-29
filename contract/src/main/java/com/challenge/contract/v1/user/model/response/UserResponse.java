package com.challenge.contract.v1.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Builder
@Document(value = "users")
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String pass;
}
