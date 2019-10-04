package com.challange.impl.file.service;

import com.challange.impl.upload.repository.UploadEntity;
import com.challange.impl.upload.service.UploadService;
import com.challange.impl.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class FileService {
    private static final String HOST = "172.17.0.2";
    private static final String USUARIO = "chapolin";
    private static final String SENHA = "chapolin";

    private FTPClient ftpClient;
    private UserRepository repository;
    private UploadService uploadService;

    public void connectServerFTP() throws IOException {
        ftpClient.connect(HOST);
        ftpClient.login(USUARIO, SENHA);
    }

    public void saveFile(MultipartFile files, String idUser) throws Exception {
        int counter = 0;
        try {
            connectServerFTP();
            if (verifyExists(idUser)) {
                ftpClient.changeWorkingDirectory(idUser);
                String nameFile = files.getOriginalFilename();

                for (String file : ftpClient.listNames()) {
                    String newName = "(" + counter + ")" + nameFile;
                    if (file.equals(newName)) {
                        counter++;
                    }
                }

                String newName = "(" + counter + ")" + nameFile;
                ftpClient.storeFile(newName, files.getInputStream());
                saveUpload(newName, idUser);
            }
        } catch (IOException e) {
            throw new Exception("Não foi possível conectar no servidor FTP. " + e.getMessage());
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new Exception("Não foi possível desconectar no servidor FTP. " + e.getMessage());
            }
        }
    }

    public Page<FTPFile> listFilesUser(String ideUser, Pageable pageable) throws Exception {
        FTPFile[] nameDirectory = null;
        try {
            connectServerFTP();
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(ideUser);
            //nameDirectory = ft.listNames();
            nameDirectory = ftpClient.listFiles();
            Page<FTPFile> files = pagedSerch(pageable, nameDirectory);
            return files;
        } catch (IOException e) {
            throw new Exception("Não foi possível conectar no servidor FTP. " + e.getMessage());
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
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
        } else {
            int toIndex = Math.min(startItem + pageSize, files.size());
            list = files.subList(startItem, toIndex);
        }

        Page<FTPFile> filesPage = new PageImpl<FTPFile>(list, PageRequest.of(currentPage, pageSize), files.size());

        return filesPage;
    }

    public void deleteFiles(String idUser, String name) throws Exception {
        System.out.println(idUser + " ++++++++++++++++++++++++ " + name);
        try {
            connectServerFTP();
            if (!verifyExists(idUser)) {
                throw new Exception("Usuário inexistente.");
            } else {
                ftpClient.changeWorkingDirectory(idUser);
                boolean fileExists = false;
                for (FTPFile file : ftpClient.listFiles()) {
                    if (file.getName().equals(name)) {
                        fileExists = true;
                        break;
                    }
                }
                if (fileExists) {
                    ftpClient.deleteFile(name);
                    deleteUpload(name);
                } else {
                    throw new Exception("Arquivo não encontrado.");
                }
            }
        } catch (IOException e) {
            throw new Exception("Não foi possível conectar no servidor FTP. " + e.getMessage());
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    private void saveUpload(String name, String idUser) {
        if (verifyExists(idUser)) {
            UploadEntity up = new UploadEntity(name, idUser);
            uploadService.save(up);
            /*String idArquivo = upload.buscarIdUpload(nome);
            compartilharArquivos(idArquivo,idUsuario);*/
        } else {
            System.out.print("Usuário inexistente.");
        }
    }

    private void deleteUpload(String nameFile) {
        try {
            uploadService.delete(nameFile);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public boolean verifyExists(String idUser) {
        return repository.existsById(idUser);
    }

    public ResponseEntity listingFiles(Model model, String id, Optional<Integer> page, Optional<Integer> size) {
        try {
            int currentPage = page.orElse(1);
            int pageSize = size.orElse(5);

            Page<FTPFile> files = listFilesUser(id, PageRequest.of(currentPage - 1, pageSize));

            model.addAttribute("files", files);
            int totalPages = files.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            return new ResponseEntity<>(files, HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }
}
