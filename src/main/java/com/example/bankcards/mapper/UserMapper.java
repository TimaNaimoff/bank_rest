package com.example.bankcards.mapper;


import com.example.bankcards.dto.*;
import com.example.bankcards.entity.*;
import org.mapstruct.*;


import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserMapper {
@Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
UserDto toDto(User user);


default Set<String> mapRoles(Set<Role> roles) {
return roles.stream().map(Role::getName).collect(Collectors.toSet());
}
}