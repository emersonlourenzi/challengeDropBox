package com.challange.contract.v1.upload;

import com.challange.impl.upload.UploadFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UploadContractFacade {
    private UploadFacade uf;

    public void listAllUploads() {
        uf.listAllUploads();
    }
}
