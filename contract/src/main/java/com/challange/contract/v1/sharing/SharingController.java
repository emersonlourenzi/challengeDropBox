package com.challange.contract.v1.sharing;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/shares")
@RestController
@AllArgsConstructor
public class SharingController {
    private SharingContractFacade scf;

    @GetMapping
    public void listingAllSharing() {
        scf.listingAllSharing();
    }
}
