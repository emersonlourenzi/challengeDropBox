package com.challange.impl.sharing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Builder
@Document(value = "shares")
public class SharingModel {
    private String id;
    private String idFile;
    private String idUser;
    private String idUserCreator;
}
