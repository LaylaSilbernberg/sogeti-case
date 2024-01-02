package com.layla.filmlandbackend.interfaces;

import com.layla.filmlandbackend.model.dto.LoginRequestDTO;
import com.layla.filmlandbackend.model.entity.FilmlandUser;

public interface UserService {
    FilmlandUser registerNewUser(LoginRequestDTO dto);
}
