package com.eventdriven.userservice.service;


import com.eventdriven.userservice.dto.UserDto;
import com.eventdriven.userservice.entity.Users;

import java.util.List;

public interface UserService {
    List<Users> allUsers();

    Long createUser(UserDto userDto);
    void updateUser(UserDto userDto);
}
