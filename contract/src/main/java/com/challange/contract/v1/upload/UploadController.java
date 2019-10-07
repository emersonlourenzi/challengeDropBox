package com.challange.contract.v1.upload;

import com.challange.impl.upload.repository.UploadEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v1/uploads")
@RestController
@AllArgsConstructor
public class UploadController {
    private UploadContractFacade facade;

    @GetMapping
    public List<UploadEntity> listAllUploads() {
        return facade.listAllUploads();
    }
}
