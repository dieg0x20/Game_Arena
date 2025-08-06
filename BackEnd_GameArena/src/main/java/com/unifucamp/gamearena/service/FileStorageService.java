package com.unifucamp.gamearena.service;

import com.unifucamp.gamearena.config.FileStorageProperties;
import com.unifucamp.gamearena.exception.FileStorageException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "pdf");

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
    }

    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);

    @PostConstruct
    public void init() throws IOException {
        log.info("Criando diretorio de upload dos comprovantes");
        Files.createDirectories(fileStorageLocation);
    }

    public String storeFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (!originalFileName.contains(".")) {
            log.warn("Tentativa de salvar um arquivo sem extensão.");
            throw new FileStorageException("Arquivo sem extensão não é permitido.");
        }

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.') + 1).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
            log.warn("Tentativa de salvar um arquivo com extensão não reconhecida: {}", fileExtension);
            throw new FileStorageException("Extensão de arquivo não permitida: " + fileExtension);
        }

        String storedFileName = UUID.randomUUID().toString() + "." + fileExtension;

        try {
            Path tempFile = Files.createTempFile("upload-", "." + fileExtension);
            file.transferTo(tempFile);

            String mimeType = Files.probeContentType(tempFile);

            if (mimeType == null || (!mimeType.startsWith("image/") && !mimeType.equals("application/pdf"))) {
                Files.delete(tempFile);
                log.warn("Tentativa de salvar um arquivo com conteúdo inválido ou potencialmente perigoso. {}", mimeType);
                throw new FileStorageException("Tipo de conteúdo inválido ou potencialmente perigoso.");
            }

            Path targetLocation = fileStorageLocation.resolve(storedFileName);
            Files.move(tempFile, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("Arquivo salvo com sucesso.");
            return storedFileName;
        } catch (IOException ex) {
            log.warn("Falha ao armazenar o arquivo: {}", ex.getMessage());
            throw new FileStorageException("Falha ao armazenar o arquivo: " + ex.getMessage());
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                log.warn("Arquivo não encontrado: {}", fileName);
                throw new FileStorageException("Arquivo não encontrado: " + fileName);
            }

            log.info("Arquivo encontrado: {}", fileName);
            return resource;

        } catch (MalformedURLException ex) {
            log.warn("Erro ao carregar um arquivo: {}", fileName);
            throw new FileStorageException("Erro ao carregar o arquivo: " + fileName);
        }
    }
}