package com.mariah.mariah_store.auth;

import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.mariah.mariah_store.model.ClienteModel;
import com.mariah.mariah_store.repository.ClienteRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final ClienteRepository repo;

    public JpaUserDetailsService(ClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClienteModel c = repo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return User.builder()
                .username(c.getEmail())
                .password(c.getSenha())
                .authorities(
                    c.getRoles().stream()
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList())
                )
                .build();
    }
}
