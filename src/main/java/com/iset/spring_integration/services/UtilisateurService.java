package com.iset.spring_integration.services;

import com.iset.spring_integration.entities.Administrateur;
import com.iset.spring_integration.entities.Developpeur;
import com.iset.spring_integration.entities.Enseignant;
import com.iset.spring_integration.entities.Utilisateur;
import com.iset.spring_integration.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService implements UserDetailsService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

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
}
