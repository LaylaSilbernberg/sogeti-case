package com.layla.filmlandbackend.model.repository;

import com.layla.filmlandbackend.model.entity.FilmlandUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmlandUserRepository extends CrudRepository<FilmlandUser, Long> {

    Optional<FilmlandUser> findByUsername(String username);

}