package com.challange.impl.upload;

import com.challange.impl.upload.repository.UploadEntity;
import com.challange.impl.upload.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UploadFacade {
    private UploadService us;

    public void listAllUploads() {
        us.listAllUploads();
    }

    public void deleteByIdUser(String id) {
        us.deleteByIdUser(id);
    }

    public String fetchIdUpload(String nameFile) {
        return us.fetchIdUpload(nameFile);
    }

    public boolean verifyExists(String idFile) {
        return us.verifyExists(idFile);
    }

    public void save(UploadEntity up) {
        us.save(up);
    }
}
