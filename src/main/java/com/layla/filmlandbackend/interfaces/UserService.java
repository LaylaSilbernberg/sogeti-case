package com.layla.filmlandbackend.interfaces;

import com.layla.filmlandbackend.controller.dto.LoginRequestDTO;
import com.layla.filmlandbackend.model.entity.FilmlandUser;

public interface UserService {
    FilmlandUser registerNewUser(LoginRequestDTO dto);
}
