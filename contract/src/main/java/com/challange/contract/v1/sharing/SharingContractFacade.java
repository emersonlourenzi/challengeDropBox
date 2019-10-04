package com.challange.contract.v1.sharing;

import com.challange.impl.sharing.SharingFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SharingContractFacade {
    private SharingFacade sf;

    public void listingAllSharing() {
        sf.listingAllSharing();
    }
}
