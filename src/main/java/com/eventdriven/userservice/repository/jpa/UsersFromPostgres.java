package com.eventdriven.userservice.repository.jpa;

import com.eventdriven.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersFromPostgres extends JpaRepository<Users, Long> {
}
