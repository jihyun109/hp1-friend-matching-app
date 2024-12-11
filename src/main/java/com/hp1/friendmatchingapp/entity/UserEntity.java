package com.hp1.friendmatchingapp.entity;

import com.hp1.friendmatchingapp.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
// Lombok 어노테이션
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @NotNull
    private String firstName;   // 이름
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private int age;
    private int ageRange;

    @NotNull
    private String profileImageUrl;

    @NotNull
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    private String chatRoomUrl;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_hobbies",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id")
    )
    private Set<HobbyEntity> hobbies;

    public void updateUser(String firstName, LocalDate birthDate, int age, int ageRange, Gender gender, String ChatRoomUrl) {
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.age = age;
        this.ageRange = ageRange;
        this.gender = gender;
        this.chatRoomUrl = ChatRoomUrl;
//        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + age +
                ", ageRange=" + ageRange +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", gender=" + gender +
                ", chatRoomUrl='" + chatRoomUrl + '\'' +
                ", email='" + email + '\'' +
                ", hobbies=" + hobbies +
                '}';
    }
}