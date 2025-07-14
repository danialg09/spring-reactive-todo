package com.example.todo.service.impl;

import com.example.todo.entity.Task;
import com.example.todo.entity.User;
import com.example.todo.exception.EntityNotFoundException;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.TaskServiceInterface;
import com.example.todo.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService implements TaskServiceInterface {

    private final TaskRepository repository;
    private final UserRepository userRepository;

    @Override
    public Flux<Task> findAll() {
        return repository.findAll().flatMap(this::fulfill);
    }

    @Override
    public Mono<Task> findById(String id) {
        return repository.findById(id).switchIfEmpty(Mono.error(
                () -> new EntityNotFoundException("Task not found"))).flatMap(this::fulfill);
    }

    @Override
    public Mono<Task> create(Task task) {
        task.setCreatedAt(Instant.now());
        return fulfill(task).flatMap(repository::save);
    }

    @Override
    public Mono<Task> addObserver(String id, String observerId) {
        return userRepository.findById(observerId).flatMap(
                user -> findById(id).flatMap(task -> {
            task.getObserverIds().add(user.getId());
            task.setUpdatedAt(Instant.now());
            return fulfill(task).flatMap(repository::save);
        }));
    }

    @Override
    public Mono<Task> update(String id, Task task) {
        return repository.findById(id).switchIfEmpty(Mono.error(
                () -> new EntityNotFoundException("Task not found"))).flatMap(t -> {
            BeanUtils.copyNonNullProperties(task, t);
            t.setId(id);
            t.setUpdatedAt(Instant.now());
            return fulfill(t).flatMap(repository::save);
        });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.findById(id).switchIfEmpty(Mono.error(
                () -> new EntityNotFoundException("Task not found"))).flatMap(t -> {
            t.getObserverIds().clear();
            t.getObservers().clear();
            return repository.delete(t);
        });
    }

    public Mono<Task> fulfill(Task task) {

        Mono<User> authorMono = userRepository.findById(task.getAuthorId());
        Mono<User> assigneeMono = userRepository.findById(task.getAssigneeId());

        Mono<Set<User>> observersMono = Flux.fromIterable(task.getObserverIds())
                .flatMap(userRepository::findById).collect(Collectors.toSet());

        return Mono.zip(authorMono, assigneeMono, observersMono).map(tuple -> {
            User author = tuple.getT1();
            User assignee = tuple.getT2();
            Set<User> observers = tuple.getT3();

            task.setAuthor(author);
            task.setAssignee(assignee);
            task.setObservers(observers);
            return task;
        });
    }
}
