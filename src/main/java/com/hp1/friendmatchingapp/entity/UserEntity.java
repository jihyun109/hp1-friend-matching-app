package com.hp1.friendmatchingapp.entity;

import com.hp1.friendmatchingapp.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
// Lombok 어노테이션
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;   // 이름
    private LocalDate birthDate;
    private int age;
    private int ageRange;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String chatRoomUrl;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_hobbies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    private Set<HobbyEntity> hobbies;
}