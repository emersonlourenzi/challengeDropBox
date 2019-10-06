package com.challange.impl.sharing;

import com.challange.impl.sharing.repository.SharingEntity;
import com.challange.impl.sharing.service.SharingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SharingFacade {
    private SharingService ss;

    public List<SharingEntity> listingAllSharing() {
        return ss.listingAllSharing();
    }

    public void deleteByIdFile(String id) {
        ss.deleteByIdFile(id);
    }

    public Page<SharingEntity> listAllByUser(Model model, String idUser, Optional<Integer> page, Optional<Integer> size) {
        return ss.listAllByUser(model, idUser, page, size);
    }

    public Page<SharingEntity> listSharedWithMe(Model model, String idUser, Optional<Integer> page, Optional<Integer> size) {
        return ss.listSharedWithMe(model, idUser, page, size);
    }

    public void share(String nameFile, String idUser, String emailByShare) throws Exception {
        ss.share(nameFile, idUser, emailByShare);
    }

    public void delete(String nameFile, String idUser) throws Exception {
        ss.delete(nameFile, idUser);
    }
}
