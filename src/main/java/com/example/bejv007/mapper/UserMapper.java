package com.example.bejv007.mapper;

import com.example.bejv007.user.UserModel;
import com.example.bejv007.user.request.UserRequest;
import com.example.bejv007.user.UserResponse;
import com.example.bejv007.user.dto.UserDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserModel userDtoToUserModel (UserDTO userDTO);

    UserDTO userRequestToUserDTO (UserRequest userRequest);

    UserDTO userModelToUserDTO (UserModel userModel);


    @Named("mapWithoutPassword")
    @Mapping(target = "password", ignore = true)
    UserDTO userDtoToUserResponse (UserResponse response);
    @InheritInverseConfiguration
    UserResponse userDtoToUserResponse (UserDTO userDTO);

    @Named("mapWithoutIdAndPassword")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserModel userResponseToUserModel (UserResponse response);
    @InheritInverseConfiguration
    UserResponse userModelToUserResponse (UserModel entity);





}
