package com.hp1.friendmatchingapp.entity;

import com.hp1.friendmatchingapp.enums.Hobby;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "hobbies")
@RequiredArgsConstructor
public class HobbyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Hobby hobbyName;

    public HobbyEntity(Hobby hobbyName) {
        this.hobbyName = hobbyName;
    }
}
