package com.eventdriven.userservice.service.impl;

import com.eventdriven.userservice.dto.UserDto;
import com.eventdriven.userservice.entity.Users;
import com.eventdriven.userservice.repository.cassandra.UsersFromCassandra;
import com.eventdriven.userservice.repository.jpa.UsersFromPostgres;
import com.eventdriven.userservice.service.UserService;
import com.eventdriven.userservice.util.JsonSerDe;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UsersFromPostgres usersFromPostgres;

    @Autowired
    private UsersFromCassandra usersFromCassandra;

    @Autowired
    private KafkaTemplate<Long, String> kafkaTemplate;

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
        Users user = new Users();
        user.setId(userDto.getId());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        this.raiseEvent(userDto);
        return this.usersFromPostgres.save(user).getId();
    }

    @Override
    @Transactional
    public Long createUserInCassandra(UserDto userDto) {
        Users user = new Users();
        user.setId(userDto.getId());
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        return this.usersFromCassandra.save(user).getId();
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        this.usersFromPostgres.findById(userDto.getId())
                .ifPresent(user -> {
                    user.setFirstname(userDto.getFirstname());
                    user.setLastname(userDto.getLastname());
                    user.setEmail(userDto.getEmail());
                    this.raiseEvent(userDto);
                });
    }

    private void raiseEvent(UserDto dto) {
        String value = JsonSerDe.toJson(dto);
        this.kafkaTemplate.sendDefault(dto.getId(), value);
    }
}
