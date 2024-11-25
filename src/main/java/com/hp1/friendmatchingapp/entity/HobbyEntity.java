package com.hp1.friendmatchingapp.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "hobbies")
@RequiredArgsConstructor
public class HobbyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hobbyName;

    public HobbyEntity(String hobbyName) {
        this.hobbyName = hobbyName;
    }
}
