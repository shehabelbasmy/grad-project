package com.mediacare.mapper;

import com.mediacare.entity.MyUser;
import com.mediacare.mvc.dto.NewUserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MyUserMapper {

    @Mapping(target = "enabled",constant = "true")
    @Mapping(target = "authority",constant = "PATIENT")
    MyUser newUserToEntityUser(NewUserDto newUserDto);

    @InheritInverseConfiguration
    @Mapping(target = "password",ignore = true)
    NewUserDto entityUserToNewUser (MyUser myUser);
}
