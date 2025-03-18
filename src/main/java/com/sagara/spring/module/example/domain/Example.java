package com.sagara.spring.module.example.domain;

import com.sagara.spring.services.BaseColumnEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "example")
@Getter
@Setter
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Example extends BaseColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "name")
    private String name;
}
