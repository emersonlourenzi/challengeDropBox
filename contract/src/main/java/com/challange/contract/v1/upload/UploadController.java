package com.challange.contract.v1.upload;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/uploads")
@RestController
@AllArgsConstructor
public class UploadController {
    private UploadContractFacade ucf;

    public void listAllUploads() {
        ucf.listAllUploads();
    }
}
