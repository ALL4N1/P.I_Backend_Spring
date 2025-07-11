package com.iset.spring_integration.controllers;

import com.iset.spring_integration.dto.LoginRequest;
import com.iset.spring_integration.dto.RegisterRequest;
import com.iset.spring_integration.entities.Utilisateur;
import com.iset.spring_integration.repositories.UtilisateurRepository;
import com.iset.spring_integration.security.JwtService;
import com.iset.spring_integration.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurService utilisateurService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );

            if (authentication.isAuthenticated()) {
                Map<String, String> infos = new HashMap<>(jwtService.generate(loginDTO.getEmail()));
                infos.put("role", authentication.getAuthorities().toString());
                return ResponseEntity.ok(infos);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String email = authentication.getName();
        Optional<Utilisateur> user = utilisateurRepository.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws IOException {
        return new ResponseEntity<>(this.utilisateurService.registerDeveloppeur(registerRequest),HttpStatus.CREATED);
    }

    @PutMapping("/update_pfp")
    public ResponseEntity<?> upload_pfp(@RequestParam("id") String id,
                                        @RequestParam("pfp") MultipartFile image) throws IOException {
        return new ResponseEntity<>(this.utilisateurService.uploadImage(id,image),HttpStatus.OK);
    }

    @PostMapping("/{id}/update_pass")
    public void updatePass(@PathVariable Long id, @RequestBody String pass) {
        Optional<Utilisateur> ut = this.utilisateurRepository.findById(id.toString());
        if(ut.isPresent()) {
            Utilisateur u = ut.get();
            u.setMdp(passwordEncoder.encode(pass));
            this.utilisateurRepository.save(u);
            ResponseEntity.status(HttpStatus.ACCEPTED).body("Password updated");
        }
        else{
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}

