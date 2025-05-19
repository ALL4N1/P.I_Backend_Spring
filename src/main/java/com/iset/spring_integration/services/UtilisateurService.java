    package com.iset.spring_integration.services;

    import com.iset.spring_integration.dto.RegisterRequest;
    import com.iset.spring_integration.entities.Administrateur;
    import com.iset.spring_integration.entities.Developpeur;
    import com.iset.spring_integration.entities.Enseignant;
    import com.iset.spring_integration.entities.Utilisateur;
    import com.iset.spring_integration.repositories.DeveloppeurRepository;
    import com.iset.spring_integration.repositories.UtilisateurRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.util.*;

    @Service
    public class UtilisateurService implements UserDetailsService {
        @Autowired
        private UtilisateurRepository utilisateurRepository;
        @Autowired
        private DeveloppeurRepository devRepository;

        private static final String UPLOAD_DIR = new File("uploads/profile_images/").getAbsolutePath() + "/";

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Utilisateur user = this.getUtilisateurByEmail(username);
            List<GrantedAuthority> authorities = new ArrayList<>();

            if (user instanceof Administrateur) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                authorities.add(new SimpleGrantedAuthority("ROLE_ENSEIGNANT"));
                authorities.add(new SimpleGrantedAuthority("ROLE_DEVELOPPEUR"));
            } else if (user instanceof Enseignant) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ENSEIGNANT"));
                authorities.add(new SimpleGrantedAuthority("ROLE_DEVELOPPEUR"));
            } else if (user instanceof Developpeur) {
                authorities.add(new SimpleGrantedAuthority("ROLE_DEVELOPPEUR"));
            }
            return new User(
                    user.getEmail(),
                    user.getMdp(),
                    true,
                    true, true, true, authorities
            );
        }

        public Utilisateur getUtilisateurByEmail(String email) throws UsernameNotFoundException{
            return this.utilisateurRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new  UsernameNotFoundException("Aucun utilisateur ne corespond à cet email"));
        }

        public Map<String,Long> registerDeveloppeur(RegisterRequest registerRequest) throws IOException {
            if(this.utilisateurRepository.findByEmail(registerRequest.getEmail()).isPresent()){
                throw new RuntimeException("Email already exists");
            }

            Developpeur dev = new Developpeur();
            dev.setEmail(registerRequest.getEmail());
            dev.setMdp(passwordEncoder.encode(registerRequest.getMdp()));
            dev.setNom(registerRequest.getNom());
            dev.setTel(Integer.parseInt(registerRequest.getTel()));

            dev = devRepository.save(dev);
            Map<String, Long> result = new HashMap<>();
            result.put("newId", dev.getId());
            return result;
        }

        public ResponseEntity<?> uploadImage(String id, MultipartFile image) throws IOException {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                return ResponseEntity.status(500).body("Server error: Unable to create upload directory.");
            }

            Developpeur dev = devRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new UsernameNotFoundException("Id not found!"));

            // Handle profile image upload
            String profileImagePath = null;
            if (image != null && !image.isEmpty()) {
                // Create upload directory if it doesn't exist
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generate unique filename
                String originalFilename = image.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFilename = dev.getId() + "_" + UUID.randomUUID().toString() + fileExtension;

                if(dev.getPfp_url() != null){
                    profileImagePath = dev.getPfp_url().replace("uploads/profile_images/","");
                }

                // Save file
                Path filePath = uploadPath.resolve(uniqueFilename);
                image.transferTo(filePath.toFile());

                profileImagePath = "uploads/profile_images/" + uniqueFilename;
                dev.setPfp_url(profileImagePath);
                devRepository.save(dev);
                return ResponseEntity.ok().body(profileImagePath);
            }
            else{
                return ResponseEntity.status(500).body("Server error: Image is empty.");
            }
        }
    }
