package com.layla.filmlandbackend.model.dto;

import java.util.Set;

public record CategoriesDTO(
        Set<CategoryDTO> availableCategories,
        Set<SubscriptionDTO> subscribedCategories
) {
}
