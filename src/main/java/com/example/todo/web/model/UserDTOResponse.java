package com.example.todo.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOResponse {

    private String id;

    private String username;

    private String password;

    private String name;

    private String email;

}
