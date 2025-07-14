package com.example.todo.mapper;

import com.example.todo.entity.User;
import com.example.todo.web.model.UserDTORequest;
import com.example.todo.web.model.UserDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UserDTORequest request);

    @Mapping(target = "id", source = "id")
    User requestToUser(String id, UserDTORequest request);

    UserDTOResponse userToDTO(User user);
}
