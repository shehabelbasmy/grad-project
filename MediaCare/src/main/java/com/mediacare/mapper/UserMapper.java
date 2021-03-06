package com.mediacare.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mediacare.entity.Patient;
import com.mediacare.entity.User;
import com.mediacare.mvc.dto.NewUserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
//
//    @Mapping(target = "enabled",constant = "true")
//    @Mapping(target = "authority",constant = "ADMIN")
//    Admin newUserToAdmin(NewUserDto newUserDto);

    @Mapping(target = "enabled",constant = "true")
    @Mapping(target = "authority",constant = "PATIENT")
    Patient newUserToPatient(NewUserDto newUserDto);

    @InheritInverseConfiguration
    @Mapping(target = "password",ignore = true)
    NewUserDto entityUserToNewUser (User myUser);
}
