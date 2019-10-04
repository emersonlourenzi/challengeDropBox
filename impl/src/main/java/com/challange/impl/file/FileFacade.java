package com.challange.impl.file;

import com.challange.impl.file.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Component
@AllArgsConstructor
public class FileFacade {
    private FileService fs;

    public void upload(MultipartFile files, String idUser) throws Exception {
        fs.saveFile(files, idUser);
    }

    public void delete(String idUser, String name) throws Exception {
        fs.deleteFiles(idUser, name);
    }

    public ResponseEntity listingFiles(Model model, String id, Optional<Integer> page, Optional<Integer> size) {
        return fs.listingFiles(model, id, page, size);
    }

}
