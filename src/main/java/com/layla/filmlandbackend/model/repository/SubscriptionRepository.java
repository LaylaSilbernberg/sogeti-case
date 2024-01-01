package com.layla.filmlandbackend.model.repository;

import com.layla.filmlandbackend.model.entity.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}