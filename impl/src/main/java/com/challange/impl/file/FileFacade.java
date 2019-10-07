package com.challange.impl.file;

import com.challange.impl.file.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
@AllArgsConstructor
public class FileFacade {
    private FileService fileService;

    public void upload(MultipartFile files, String idUser) throws Exception {
        fileService.saveFile(files, idUser);
    }

    public void delete(String idUser, String name) throws Exception {
        fileService.deleteFiles(idUser, name);
    }

    public ResponseEntity listingFiles(Model model, String id, Optional<Integer> page, Optional<Integer> size) {
        return fileService.listingFiles(model, id, page, size);
    }

    public InputStream downloadFile(String id, String nameFile) throws IOException {
        return fileService.downloadFile(id, nameFile);
    }
}
