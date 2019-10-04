package com.challange.contract.v1.file;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("v1/files")
public class FileController {
    private FileContractFacade fcf;

    @PostMapping
    public void upload(@RequestParam MultipartFile files, @RequestParam String idUser) throws Exception {
        fcf.upload(files, idUser);
    }

    @DeleteMapping()
    public void delete(@PathParam("idUser") String idUser, @PathParam("name") String name) throws Exception {
        System.out.println("1 - " + idUser + " +++++++++++++++++++++ " + name);
        fcf.delete(idUser, name);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity listingFiles(Model model, @PathVariable("id") String id, @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) {
        return fcf.listingFiles(model, id, page, size);
    }
}
