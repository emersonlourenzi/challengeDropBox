package com.challange.contract.v1.sharing;

import com.challange.impl.sharing.SharingFacade;
import com.challange.impl.sharing.repository.SharingEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SharingContractFacade {
    private SharingFacade sf;

    public List<SharingEntity> listingAllSharing() {
        return sf.listingAllSharing();
    }

    public Page<SharingEntity> listAllByUser(Model model, String idUser, Optional<Integer> page, Optional<Integer> size) {
        return sf.listAllByUser(model, idUser, page, size);
    }

    public Page<SharingEntity> listSharedWithMe(Model model, String idUser, Optional<Integer> page, Optional<Integer> size) {
        return sf.listSharedWithMe(model, idUser, page, size);
    }

    public void share(String nameFile, String idUser, String emailByShare) throws Exception {
        sf.share(nameFile, idUser, emailByShare);
    }

    public void delete(String nameFile, String idUser) throws Exception {
        sf.delete(nameFile, idUser);
    }
}
