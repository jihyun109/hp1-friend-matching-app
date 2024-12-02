package com.hp1.friendmatchingapp.dto;

import com.hp1.friendmatchingapp.enums.Gender;
import com.hp1.friendmatchingapp.enums.Hobby;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserInfoHobbiesResponseDTO {
    private String profileImageUrl;
    private String firstName;
    private LocalDate birthDate;
    private Gender gender;
    private String chatRoomUrl;
    private Optional<List<Hobby>> hobbies;

}
