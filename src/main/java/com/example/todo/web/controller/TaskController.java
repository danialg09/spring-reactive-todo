package com.example.todo.web.controller;

import com.example.todo.mapper.TaskMapper;
import com.example.todo.service.impl.TaskService;
import com.example.todo.web.model.TaskDTORequest;
import com.example.todo.web.model.TaskDTOResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;
    private final TaskMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public Flux<TaskDTOResponse> findAll() {
        return service.findAll().map(mapper::taskToResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public Mono<ResponseEntity<TaskDTOResponse>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(mapper::taskToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/observer/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public Mono<ResponseEntity<TaskDTOResponse>> addObserver(@PathVariable String id, @RequestParam String observer) {
        return service.addObserver(id, observer)
                .map(mapper::taskToResponse)
                .map(ResponseEntity::ok).
                defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<ResponseEntity<TaskDTOResponse>> save(@Valid @RequestBody TaskDTORequest request) {
        return service.create(mapper.requestToTask(request))
                .map(mapper::taskToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<ResponseEntity<TaskDTOResponse>> update(@PathVariable String id, @Valid @RequestBody TaskDTORequest request) {
        return service.update(id, mapper.requestToTask(request))
                .map(mapper::taskToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }
}
