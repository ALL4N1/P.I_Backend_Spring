package com.iset.spring_integration.controllers;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "*")
public class PdfController {

    @GetMapping("/pdf/{filename:.+}")
    public ResponseEntity<Resource> servePdf(@PathVariable String filename) {
        try {
            // Create paths to find the file in different potential locations
            Path certPath = Paths.get("uploads/certifications").resolve(filename).normalize();
            Resource resource = new UrlResource(certPath.toUri());

            // Check if file exists and is readable
            if(resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/diagnose/certifications")
    public ResponseEntity<?> diagnoseFileSystem() {
        Path certPath = Paths.get("uploads/certifications");
        boolean exists = certPath.toFile().exists();
        boolean isDir = certPath.toFile().isDirectory();
        String absolutePath = certPath.toFile().getAbsolutePath();
        return ResponseEntity.ok(java.util.Map.of(
                "directoryExists", exists,
                "isDirectory", isDir,
                "absolutePath", absolutePath,
                "canRead", certPath.toFile().canRead()
        ));
    }
}