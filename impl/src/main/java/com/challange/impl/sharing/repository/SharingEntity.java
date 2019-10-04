package com.challange.impl.sharing.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Builder
@Document(value = "shares")
public class SharingEntity {
    private String id;
    private String idFile;
    private String idUser;
    private String idUserCreator;
}
