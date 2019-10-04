package com.challange.contract.v1.file;

import com.challange.impl.file.FileFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@AllArgsConstructor
@Component
public class FileContractFacade {
    private FileFacade ff;

    public void upload(MultipartFile files, String idUser) throws Exception {
        ff.upload(files, idUser);
    }

    public void delete(String idUser, String name) throws Exception {
        ff.delete(idUser, name);
    }

    public ResponseEntity listingFiles(Model model, String id, Optional<Integer> page, Optional<Integer> size) {
        return ff.listingFiles(model, id, page, size);
    }

}
