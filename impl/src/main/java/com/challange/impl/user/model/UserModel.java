package com.challange.impl.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
@Builder
@Document(value = "users")
public class UserModel {
    @Id
    private String id;
    @NotNull
    @NotEmpty(message = "Required Name field.")
    private String name;
    @NotNull
    @Email(message = "Enter a valid email address.")
    @NotEmpty(message = "Required to fill in the email field.")
    private String email;
    @NotNull
    @NotEmpty(message = "Required to fill in the password field.")
    private String pass;
}
