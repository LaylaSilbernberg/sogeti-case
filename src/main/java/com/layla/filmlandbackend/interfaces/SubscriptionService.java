package com.layla.filmlandbackend.interfaces;

import com.layla.filmlandbackend.controller.dto.CategoriesDTO;
import com.layla.filmlandbackend.enums.SubscriptionCategory;
import com.layla.filmlandbackend.model.entity.FilmlandUser;
import com.layla.filmlandbackend.model.entity.Subscription;

import java.util.Set;

public interface SubscriptionService {
    CategoriesDTO getAvailableCategories(String username);

    Set<Subscription> subscribe(String username, SubscriptionCategory category);

    Set<FilmlandUser> addSubscriber(String username, String clientName, SubscriptionCategory category);

}
