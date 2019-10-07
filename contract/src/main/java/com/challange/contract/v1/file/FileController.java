package com.challange.contract.v1.file;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("v1/files")
public class FileController {
    private FileContractFacade facade;

    @PostMapping
    public void upload(@RequestParam MultipartFile files, @RequestParam String idUser) throws Exception {
        facade.upload(files, idUser);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathParam("idUser") String idUser, @PathParam("name") String name) throws Exception {
        facade.delete(idUser, name);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity listingFiles(Model model,
                                       @PathVariable("id") String id,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {
        return facade.listingFiles(model, id, page, size);
    }

    @GetMapping(path = "/download/{id}")
    public InputStream downloadFile(@PathVariable("id") String id,
                                    @RequestParam("nameFile") String nameFile) throws IOException {
        return facade.downloadFile(id, nameFile);
    }
}
