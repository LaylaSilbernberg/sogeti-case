package com.layla.filmlandbackend.controller;

import com.layla.filmlandbackend.controller.dto.*;
import com.layla.filmlandbackend.interfaces.SubscriptionService;
import com.layla.filmlandbackend.model.dto.*;
import com.layla.filmlandbackend.model.entity.FilmlandUser;
import com.layla.filmlandbackend.model.entity.Subscription;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SubscriptionController {

    private final Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    private SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping("subscriptions")
    public ResponseEntity<CategoriesDTO> getSubscriptions(@RequestBody @NotNull @NotBlank String username){
        return ResponseEntity.ok(service.getAvailableCategories(username));
    }

    @PostMapping("subscriptions")
    public ResponseEntity<ResponseDTO> addSubscription(@RequestBody @Validated CreateSubscriptionDTO dto){

        Set<SubscriptionDTO> subscriptions = service.subscribe(dto.username(), dto.category())
                .stream()
                .map(Subscription::makeDto)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new ResponseDTO("Subscription added!",
                "Successfully subscribed to %s.".formatted(dto.category().getName())));

    }

    @PatchMapping("subscriptions")
    public ResponseEntity<ResponseDTO> addUserToSubscription(@RequestBody @Validated AddSubscriberDTO dto){
        Set<FilmlandUser> users = service.addSubscriber(dto.email(), dto.customer(), dto.subscribedCategory());
        String subscribers = users
                .stream()
                .map(FilmlandUser::getUsername)
                .collect(Collectors.joining(", "));

        return ResponseEntity.ok(new ResponseDTO("%s successfully added to subscription!".formatted(dto.customer()),
                "Current subscribers to %s: %s".formatted(dto.subscribedCategory().getName(), subscribers)));
    }

}
