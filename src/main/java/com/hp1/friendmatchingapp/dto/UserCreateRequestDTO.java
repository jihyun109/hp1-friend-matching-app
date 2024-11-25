package com.hp1.friendmatchingapp.dto;

import com.hp1.friendmatchingapp.enums.Gender;
import com.hp1.friendmatchingapp.enums.Hobby;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Builder
@Getter
public class UserCreateRequestDTO {
    private String username;
    private String password;
    private String firstName;
    private String birthDate;
    private Gender gender;
    private String chatRoomUrl;
    private String email;
    @Builder.Default
    private Set<Hobby> hobbies = new HashSet<>();
}