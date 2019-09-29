package com.challenge.impl.file.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Service
@AllArgsConstructor
public class FileService {
    private static final String HOST = "172.17.0.2";
    private static final String USUARIO = "chapolin";
    private static final String SENHA = "chapolin";
    private int counter = 0;

    FTPClient ft = new FTPClient();

    private void connectServerFTP() throws IOException {
        ft.connect(HOST);
        ft.login(USUARIO, SENHA);
    }

    public void saveFile(MultipartFile mpf, String idUser) throws Exception {
        try {
            connectServerFTP();
            ft.changeWorkingDirectory(idUser);
            String nameFile = mpf.getOriginalFilename();
            for (String file : ft.listNames()) {
                String newName = "(" + counter + ")" + nameFile;
                if (file.equals(newName)) {
                    counter++;
                }
            }
            String newName = "(" + counter + ")" + nameFile;
            ft.storeFile(newName, mpf.getInputStream());
            saveUpload(newName, idUser);
        }
        catch (IOException e) {
            throw new Exception("Não foi possível conectar no servidor FTP. " + e.getMessage());
        }
        finally {
            try {
                ft.logout();
                ft.disconnect();
            }
            catch (IOException e) {
                throw new Exception("Não foi possível desconectar no servidor FTP. " + e.getMessage());
            }
        }
    }

    private void saveUpload(String name, String idUser) {

    }
}
