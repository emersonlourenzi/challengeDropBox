package com.challange.impl.sharing;

import com.challange.impl.sharing.service.SharingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SharingFacade {
    private SharingService ss;

    public void listingAllSharing() {
        ss.listingAllSharing();
    }

    public void deleteByIdFile(String id) {
        ss.deleteByIdFile(id);
    }
}
