package com.challenge.impl.file.service;

import lombok.AllArgsConstructor;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            if (user.verifyExists(idUser)) {
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

    public Page<FTPFile> listFilesUser(String ideUser, Pageable pageable) throws Exception {
        FTPFile[] nameDirectory = null;
        try {
            connectServerFTP();
            ft.enterLocalPassiveMode();
            ft.changeWorkingDirectory(ideUser);
            //nameDirectory = ft.listNames();
            nameDirectory = ft.listFiles();
            Page<FTPFile> files = pagedSerch(pageable, nameDirectory);
            return files;
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
                throw new Exception("Não foi possível desconectar do servidor FTP. " + e.getMessage());
            }
        }
    }

    private Page<FTPFile> pagedSerch(Pageable pageable, FTPFile[] arqs) {
        List<FTPFile> files = Arrays.asList(arqs);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<FTPFile> list;
        if (files.size() < startItem) {
            list = Collections.emptyList();
        }
        else {
            int toIndex = Math.min(startItem + pageSize, files.size());
            list = files.subList(startItem, toIndex);
        }

        Page<FTPFile> filesPage = new PageImpl<FTPFile>(list, PageRequest.of(currentPage, pageSize), files.size());

        return filesPage;
    }

    public void deleteFiles(String idUser, String name) throws Exception {
        try {
            connectServerFTP();
            if (!user.verifyExists(idUser)) {
                throw new Exception("Usuário inexistente.");
            }
            else {
                ft.changeWorkingDirectory(idUser);
                boolean fileExists = false;
                for (FTPFile file : ft.listFiles()) {
                    if (file.getName().equals(name)) {
                        fileExists = true;
                        break;
                    }
                }
                if (fileExists) {
                    ft.deleteFile(name);
                    deleteUpload(name);
                }
                else {
                    throw new Exception("Arquivo não encontrado.");
                }
            }
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
                e.getMessage();
            }
        }
    }

    private void saveUpload(String name, String idUser) {
        if (user.verifyExists(idUser)) {
            Upload up = new Upload(name, idUser);
            upload.save(up);
            /*String idArquivo = upload.buscarIdUpload(nome);
            compartilharArquivos(idArquivo,idUsuario);*/
        }
        else {
            System.out.print("Usuário inexistente.");
        }
    }

    private void deleteUpload(String nameFile) {

    }


}
