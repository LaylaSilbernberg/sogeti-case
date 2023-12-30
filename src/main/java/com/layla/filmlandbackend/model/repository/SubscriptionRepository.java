package com.layla.filmlandbackend.model.repository;

import com.layla.filmlandbackend.model.entity.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}