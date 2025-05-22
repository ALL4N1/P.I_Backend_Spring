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
import org.springframework.security.core.context.SecurityContextHolder;
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

    @PostMapping("/update_pfp")
    public ResponseEntity<?> upload_pfp(@RequestParam("id") String id,
                                        @RequestParam("pfp") MultipartFile image) throws IOException {
        return new ResponseEntity<>(this.utilisateurService.uploadImage(id,image),HttpStatus.OK);
    }

    @PostMapping("/{id}/update_pass")
    public ResponseEntity<?> updatePass(@PathVariable Long id, @RequestBody String pass) {
        System.out.println("Password update request for ID: " + id);

        try {
            // Clean up the password string - sometimes it can include quotes or whitespace
            String cleanPassword = pass.trim();
            if (cleanPassword.startsWith("\"") && cleanPassword.endsWith("\"")) {
                cleanPassword = cleanPassword.substring(1, cleanPassword.length() - 1);
            }

            System.out.println("Looking up user with ID: " + id);
            Optional<Utilisateur> ut = this.utilisateurRepository.findById(id);
            if (!ut.isPresent()) {
                System.out.println("User not found with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            Utilisateur u = ut.get();
            u.setMdp(passwordEncoder.encode(cleanPassword));
            System.out.println("Saving updated password for user: " + u.getEmail());
            this.utilisateurRepository.save(u);
            System.out.println("Password updated successfully for user ID: " + id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password updated");
        } catch (Exception e) {
            System.err.println("Error updating password: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating password: " + e.getMessage());
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, Object> profileData) {
        // Get the authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Utilisateur user = userOpt.get();

        try {
            // Update user fields from the received data
            if (profileData.containsKey("nom")) {
                user.setNom(profileData.get("nom").toString());
            }

            if (profileData.containsKey("email")) {
                // Check if email already exists and is not the current user's email
                if (!profileData.get("email").toString().equals(user.getEmail())) {
                    Optional<Utilisateur> existingUser = utilisateurRepository.findByEmail(profileData.get("email").toString());
                    if (existingUser.isPresent()) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
                    }
                }
                user.setEmail(profileData.get("email").toString());
            }

            if (profileData.containsKey("tel")) {
                Integer tel;
                if (profileData.get("tel") instanceof Integer) {
                    tel = (Integer) profileData.get("tel");
                } else {
                    tel = Integer.parseInt(profileData.get("tel").toString());
                }
                user.setTel(tel);
            }

            // Save the updated user
            utilisateurRepository.save(user);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating profile: " + e.getMessage());
        }
    }
}