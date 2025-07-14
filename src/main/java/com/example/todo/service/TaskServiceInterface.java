package com.example.todo.service;

import com.example.todo.entity.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskServiceInterface {
    Flux<Task> findAll();
    Mono<Task> findById(String id);
    Mono<Task> create(Task task);
    Mono<Task> addObserver(String id, String observer);
    Mono<Task> update(String id, Task task);
    Mono<Void> deleteById(String id);
}
