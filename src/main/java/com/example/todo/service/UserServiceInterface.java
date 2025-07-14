package com.example.todo.service;

import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserServiceInterface {
    Flux<User> findAll();
    Mono<User> findById(String id);
    Mono<User> create(User user, Role role);
    Mono<User> update(String id, User user);
    Mono<Void> deleteById(String id);
}
