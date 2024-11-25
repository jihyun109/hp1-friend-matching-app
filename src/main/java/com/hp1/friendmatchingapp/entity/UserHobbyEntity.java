package com.hp1.friendmatchingapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserHobbyId.class) // 복합키 사용
@Table(name = "user_hobbies")
public class UserHobbyEntity {
    @Id
    @Column(name = "hobby_id")
    private Long hobbyId;

    @Id
    @Column(name = "user_id")
    private Long userId;

//    @Id
//    @ManyToOne
//    @JoinColumn(name = "user_id", insertable = false, updatable = false)
//    private UserEntity user;
//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "hobby_id", insertable = false, updatable = false)
//    private HobbyEntity hobby;

    // Getter, Setter, Constructor, 등
}