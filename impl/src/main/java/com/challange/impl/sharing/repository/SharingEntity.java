package com.challange.impl.sharing.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(value = "shares")
public class SharingEntity {
    private String id;
    private String idFile;
    private String idUser;
    private String idUserCreator;

    public SharingEntity(String idFile, String idUser, String idUserCreator) {
        this.idFile = idFile;
        this.idUser = idUser;
        this.idUserCreator = idUserCreator;
    }
}
