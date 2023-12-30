package com.layla.filmlandbackend.controller.dto;

import com.layla.filmlandbackend.enums.SubscriptionCategory;

import java.util.Set;

public record AvailableCategoriesDTO(
        Set<SubscriptionCategory> availableCategories
) {
}
