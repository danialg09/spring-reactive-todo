package com.example.todo.web.controller;

import com.example.todo.entity.Role;
import com.example.todo.entity.RoleType;
import com.example.todo.mapper.UserMapper;
import com.example.todo.service.impl.UserService;
import com.example.todo.web.model.UserDTORequest;
import com.example.todo.web.model.UserDTOResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public Flux<UserDTOResponse> findAll() {
        return service.findAll()
                .map(mapper::userToDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public Mono<ResponseEntity<UserDTOResponse>> findById(@PathVariable String id) {
        return service.findById(id)
                .map(mapper::userToDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'MANAGER')")
    public Mono<ResponseEntity<String>> getProfile(Mono<Principal> principal) {
        return principal.map(Principal::getName)
                .map(name -> ResponseEntity.ok("Method getProfile called. Username: " + name));
    }

    @PostMapping("/account")
    public Mono<ResponseEntity<UserDTOResponse>> save(@Valid @RequestBody UserDTORequest request, @RequestParam RoleType roleType) {
        return service.create(mapper.requestToUser(request), Role.fromAuthority(roleType))
                .map(mapper::userToDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public Mono<ResponseEntity<UserDTOResponse>> update(@PathVariable String id, @Valid @RequestBody UserDTORequest request) {
        return service.update(id, mapper.requestToUser(request))
                .map(mapper::userToDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
