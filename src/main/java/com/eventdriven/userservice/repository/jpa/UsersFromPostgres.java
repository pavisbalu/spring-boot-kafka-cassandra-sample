package com.eventdriven.userservice.repository.jpa;

import com.eventdriven.userservice.entity.Users;
import org.springframework.data.repository.Repository;

public interface UsersFromPostgres extends Repository<Users, Long> {
}
