package com.mediacare.mapper;

import com.mediacare.entity.Admin;
import com.mediacare.entity.Patient;
import com.mediacare.entity.User;
import com.mediacare.mvc.dto.NewUserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "enabled",constant = "true")
    @Mapping(target = "authority",constant = "ADMIN")
    Admin newUserToAdmin(NewUserDto newUserDto);

    @Mapping(target = "enabled",constant = "true")
    @Mapping(target = "authority",constant = "PATIENT")
    Patient newUserToPatient(NewUserDto newUserDto);

    @InheritInverseConfiguration
    @Mapping(target = "password",ignore = true)
    NewUserDto entityUserToNewUser (User myUser);
}
