package com.iset.spring_integration.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;


@Controller
@RequestMapping("/ressource")
public class RessourceController {
    @GetMapping("/video/{filename}")
    public ResponseEntity<Resource> serveVideo(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get("uploads").resolve(filename);
        Resource file = new UrlResource(filePath.toUri());

        if (file.exists() && file.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
