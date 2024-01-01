package com.layla.filmlandbackend.model.service;

import com.layla.filmlandbackend.controller.dto.LoginRequestDTO;
import com.layla.filmlandbackend.enums.AuthRoles;
import com.layla.filmlandbackend.exception.UserAlreadyExistsException;
import com.layla.filmlandbackend.interfaces.UserService;
import com.layla.filmlandbackend.model.entity.FilmlandUser;
import com.layla.filmlandbackend.model.repository.FilmlandUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmlandUserService implements UserService {
    private final FilmlandUserRepository repo;
    private final PasswordEncoder encoder;

    public FilmlandUserService(FilmlandUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public FilmlandUser registerNewUser(LoginRequestDTO dto) throws UserAlreadyExistsException{
        Optional<FilmlandUser> user = repo.findByUsername(dto.username());
        if(user.isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }

        FilmlandUser newUser = new FilmlandUser(dto.username(),
                encoder.encode(dto.password()),
                AuthRoles.USER.name());

        return repo.save(newUser);
    }

}
