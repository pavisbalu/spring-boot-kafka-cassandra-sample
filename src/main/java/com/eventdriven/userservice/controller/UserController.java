package com.eventdriven.userservice.controller;

import com.eventdriven.userservice.dto.UserDto;
import com.eventdriven.userservice.entity.Users;
import com.eventdriven.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-service")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Long createUser(@RequestBody UserDto userDto) {
        return this.userService.createUserInPostgres(userDto);

    }

    @PutMapping("/update")
    public void updateUser(@RequestBody UserDto userDto) {
        this.userService.updateUserInPostgres(userDto);
    }

    @GetMapping("/postgres")
    public List<Users> allUsersFromPostgres() {
        return this.userService.allUsersFromPostgres();
    }

    @GetMapping("/cassandra")
    public List<Users> allUsersFromCassandra() {
        return this.userService.allUsersFromCassandra();
    }
}
