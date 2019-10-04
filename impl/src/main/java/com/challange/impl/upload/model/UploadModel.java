package com.challange.contract.v1.upload.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@Builder
@Document(value = "uploads")
public class UploadModel {
    private String id;
    private String nameFile;
    private String idUserCreator;

    public UploadModel(String nameFile, String idUserCreator) {
        this.nameFile = nameFile;
        this.idUserCreator = idUserCreator;
    }
}
