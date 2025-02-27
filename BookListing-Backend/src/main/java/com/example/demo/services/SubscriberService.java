package com.example.demo.services;

import com.example.demo.entities.Subscriber;
import com.example.demo.repositories.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriberService{

    @Autowired
    private SubscriberRepository subscriberRepository;

    public Optional<Subscriber> findByEmail(String email) {
        return Optional.ofNullable(subscriberRepository.findByEmail(email));
    }

    public Subscriber save(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }



}
