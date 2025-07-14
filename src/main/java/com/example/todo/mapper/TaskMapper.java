package com.example.todo.mapper;

import com.example.todo.entity.Task;
import com.example.todo.web.model.TaskDTORequest;
import com.example.todo.web.model.TaskDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface TaskMapper {

    Task requestToTask(TaskDTORequest request);

    TaskDTOResponse taskToResponse(Task task);

    default Task requestToTask(String id, TaskDTORequest request) {
        Task task = requestToTask(request);
        task.setId(id);
        return task;
    }

}
