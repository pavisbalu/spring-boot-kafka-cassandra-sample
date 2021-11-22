package com.eventdriven.userservice.service.impl;

import com.eventdriven.userservice.dto.UserDto;
import com.eventdriven.userservice.entity.Users;
import com.eventdriven.userservice.events.CreateUserEvent;
import com.eventdriven.userservice.events.DeleteUserEvent;
import com.eventdriven.userservice.events.Event;
import com.eventdriven.userservice.events.UpdateUserEvent;
import com.eventdriven.userservice.repository.cassandra.UsersFromCassandra;
import com.eventdriven.userservice.repository.jpa.UsersFromPostgres;
import com.eventdriven.userservice.service.UserService;
import com.eventdriven.userservice.util.JsonSerDe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersFromPostgres usersFromPostgres;

    @Autowired
    private UsersFromCassandra usersFromCassandra;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public List<Users> allUsersFromPostgres() {
        return this.usersFromPostgres.findAll();
    }

    @Override
    public List<Users> allUsersFromCassandra() {
        return this.usersFromCassandra.findAll();
    }

    @Override
    @Transactional
    public Long createUserInPostgres(UserDto userDto) {
        Users user = parseUser(userDto);
        this.raiseEvent(new CreateUserEvent(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail()));
        try {
            Users savedUser = this.usersFromPostgres.save(user);
            return savedUser.getId();
        } catch (Exception e) {
            this.raiseEvent(new DeleteUserEvent(user.getId()));
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Long createUserInCassandra(UserDto userDto) {
        Users user = parseUser(userDto);
        return this.usersFromCassandra.save(user).getId();
    }

    @Override
    @Transactional
    public void updateUserInPostgres(UserDto userDto) {
        this.usersFromPostgres.findById(userDto.getId())
                .ifPresent(updatedUser -> {
                    updatedUser.setFirstname(userDto.getFirstname());
                    updatedUser.setLastname(userDto.getLastname());
                    updatedUser.setEmail(userDto.getEmail());
                    this.usersFromPostgres.save(updatedUser);
                    this.raiseEvent(new UpdateUserEvent(updatedUser.getId(), updatedUser.getFirstname(), updatedUser.getLastname(), updatedUser.getEmail()));
                });
    }

    @Override
    @Transactional
    public void updateUserInCassandra(UserDto userDto) {
        this.usersFromCassandra.findById(userDto.getId())
                .ifPresent(user -> {
                    user.setFirstname(userDto.getFirstname());
                    user.setLastname(userDto.getLastname());
                    user.setEmail(userDto.getEmail());
                });
    }

    @Override
    @Transactional
    public void deleteUserFromCassandra(Long userId) {
        this.usersFromCassandra.deleteById(userId);
    }

    private void raiseEvent(Event event) {
        String value = JsonSerDe.toJson(event);
        this.kafkaTemplate.sendDefault(event.getId().toString(), value);
    }

    private Users parseUser(UserDto userDto) {
        Users user = new Users();
        user.setId(userDto.getId());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
