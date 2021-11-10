package com.eventdriven.userservice.entity;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import javax.persistence.*;


@Table
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@org.springframework.data.cassandra.core.mapping.Table
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKey
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

}
