package com.example.todo.service.impl;

import com.example.todo.entity.Role;
import com.example.todo.entity.User;
import com.example.todo.exception.EntityNotFoundException;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.UserServiceInterface;
import com.example.todo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException("Username not found")));
    }

    @Override
    public Mono<User> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException("User not found")));
    }

    @Override
    public Mono<User> create(User user, Role role) {
        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);
        return repository.save(user);
    }

    @Override
    public Mono<User> update(String id, User user) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException("User not found")))
                .flatMap(u -> {
                    BeanUtils.copyNonNullProperties(user, u);
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    return repository.save(u);
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(() -> new EntityNotFoundException("User not found")))
                .flatMap(repository::delete);
    }
}
