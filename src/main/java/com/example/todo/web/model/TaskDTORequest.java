package com.example.todo.web.model;

import com.example.todo.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTORequest {

    @NotBlank(message = "Name should not be blank")
    private String name;

    private String description;

    @NotNull(message = "Status should not be null")
    private TaskStatus status;

    @NotBlank(message = "Author ID should not be blank")
    private String authorId;

    @NotBlank(message = "Assignee ID should not be blank")
    private String assigneeId;

    private Set<String> observerIds;
}
