package com.eventdriven.userservice.repository.cassandra;

import com.eventdriven.userservice.entity.Users;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UsersRepository extends CassandraRepository<Users, Long> {
}
