package com.eventdriven.userservice.service;


import com.eventdriven.userservice.dto.UserDto;
import com.eventdriven.userservice.entity.Users;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {
    List<Users> allUsersFromPostgres();

    List<Users> allUsersFromCassandra();

    Long createUserInPostgres(UserDto userDto);

    @Transactional
    Long createUserInCassandra(UserDto userDto);

    void updateUser(UserDto userDto);
}
