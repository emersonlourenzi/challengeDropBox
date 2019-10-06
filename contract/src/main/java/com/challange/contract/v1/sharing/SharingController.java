package com.challange.contract.v1.sharing;

import com.challange.impl.sharing.repository.SharingEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/v1/shares")
@RestController
@AllArgsConstructor
public class SharingController {
    private SharingContractFacade scf;

    @GetMapping
    public List<SharingEntity> listingAllSharing() {
        return scf.listingAllSharing();
    }

    @GetMapping(path = "/{id}")
    public Page<SharingEntity> ListAllByUser(Model model, @PathVariable("id") String idUser, @RequestParam("page") Optional<Integer> page,
                                             @RequestParam("size") Optional<Integer> size) {
        return scf.listAllByUser(model, idUser, page, size);
    }

    @GetMapping(path = "/withme/{id}")
    public Page<SharingEntity> listSharedWithMe(Model model, @PathVariable("id") String idUser, @RequestParam("page") Optional<Integer> page,
                                                @RequestParam("size") Optional<Integer> size) {
        return scf.listSharedWithMe(model, idUser, page, size);
    }

    @PostMapping(path = "/{id}")
    public void share(@RequestParam("nameFile") String nameFile, @PathVariable("id") String idUser, @RequestParam("emailByShare") String emailByShare) throws Exception {
        scf.share(nameFile, idUser, emailByShare);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@RequestParam("nameFile") String nameFile, @PathVariable("id") String idUser) throws Exception {
        scf.delete(nameFile, idUser);
    }

}
