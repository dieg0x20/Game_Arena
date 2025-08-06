package com.unifucamp.gamearena.controller;

import com.unifucamp.gamearena.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    public FileStorageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    private static final Logger log = LoggerFactory.getLogger(FileStorageController.class);

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        log.info("Nova requisição de upload de arquivo.");
        String storedFileName = fileStorageService.storeFile(file);

        String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(storedFileName)
                .toUriString();

        return ResponseEntity.ok(downloadUrl);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                                 HttpServletRequest request) throws IOException {

        log.info("Nova requisição de download de arquivo.");
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

