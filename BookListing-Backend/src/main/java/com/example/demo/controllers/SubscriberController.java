package com.example.demo.controllers;

import com.example.demo.entities.Subscriber;
import com.example.demo.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscribe")
public class SubscriberController {
    @Autowired
    private SubscriberService subscriberService;

    @PostMapping
    public ResponseEntity<String> subscribeUser(@RequestBody Subscriber subscriber) {
        // Check if the email is already subscribed
        if (subscriberService.findByEmail(subscriber.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email is already subscribed", HttpStatus.BAD_REQUEST);
        }

        // If email is not subscribed, save the new subscriber
        subscriberService.save(subscriber);

        return new ResponseEntity<>("Subscription successful", HttpStatus.CREATED);
    }
}
