package com.example.todo.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTORequest {

    @NotBlank(message = "Name cannot be blank")
    private String username;

    @NotBlank(message = "Name cannot be blank")
    private String password;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String email;

}
