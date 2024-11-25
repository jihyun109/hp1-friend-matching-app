package com.hp1.friendmatchingapp.dto;

import com.hp1.friendmatchingapp.enums.Gender;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserMatchingResponseDto {
    private Long id;
    private String firstname;
    private int age;
    private Gender gender;
}
