package com.layla.filmlandbackend.model.service;

import com.layla.filmlandbackend.model.FilmlandUserDetails;
import com.layla.filmlandbackend.model.repository.FilmlandUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FilmlandUserDetailsService implements UserDetailsService {
    private final FilmlandUserRepository userRepository;

    public FilmlandUserDetailsService(FilmlandUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .map(FilmlandUserDetails::new)
                .orElseThrow(()
                        -> new UsernameNotFoundException(String.format("User not found: %s", username)));
    }
}
