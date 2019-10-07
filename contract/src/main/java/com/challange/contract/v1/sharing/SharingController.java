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
    private SharingContractFacade facade;

    @GetMapping
    public List<SharingEntity> listingAllSharing() {
        return facade.listingAllSharing();
    }

    @GetMapping(path = "/{id}")
    public Page<SharingEntity> ListAllByUser(Model model, @PathVariable("id") String idUser, @RequestParam("page") Optional<Integer> page,
                                             @RequestParam("size") Optional<Integer> size) {
        return facade.listAllByUser(model, idUser, page, size);
    }

    @GetMapping(path = "/withme/{id}")
    public Page<SharingEntity> listSharedWithMe(Model model, @PathVariable("id") String idUser, @RequestParam("page") Optional<Integer> page,
                                                @RequestParam("size") Optional<Integer> size) {
        return facade.listSharedWithMe(model, idUser, page, size);
    }

    @PostMapping(path = "/{id}")
    public void share(@RequestParam("nameFile") String nameFile, @PathVariable("id") String idUser, @RequestParam("emailByShare") String emailByShare) throws Exception {
        facade.share(nameFile, idUser, emailByShare);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@RequestParam("emailShared") String emailWithWhomYouShared, @RequestParam("nameFile") String nameFile, @PathVariable("id") String idUser) throws Exception {
        facade.delete(emailWithWhomYouShared, nameFile, idUser);
    }
}
