package com.challange.contract.v1.upload;

import com.challange.impl.upload.UploadFacade;
import com.challange.impl.upload.repository.UploadEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UploadContractFacade {
    private UploadFacade uf;

    public List<UploadEntity> listAllUploads() {
        return uf.listAllUploads();
    }
}
