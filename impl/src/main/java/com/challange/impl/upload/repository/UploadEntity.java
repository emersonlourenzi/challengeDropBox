package com.challange.impl.upload.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Builder
@Document(value = "uploads")
public class UploadEntity {
    private String id;
    private String nameFile;
    private String idUserCreator;

    public UploadEntity(String nameFile, String idUserCreator) {
        this.nameFile = nameFile;
        this.idUserCreator = idUserCreator;
    }
}
