package com.nuevospa.taskmanagement.mapper;

import com.nuevospa.taskmanagement.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "status", target = "status.name")
    @Mapping(source = "user", target = "user.name")
    TaskEntity toEntity(org.openapitools.model.Task taskDto);

    @Mapping(source = "status.name", target = "status")
    @Mapping(source = "user.name", target = "user")
    Task toDto(TaskEntity taskEntity);


}
