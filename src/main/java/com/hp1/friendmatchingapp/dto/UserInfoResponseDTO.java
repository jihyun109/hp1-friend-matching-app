package com.hp1.friendmatchingapp.dto;

import com.hp1.friendmatchingapp.enums.Gender;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserInfoResponseDTO {
    private String profileImageUrl;
    private String firstName;
    private LocalDate birthDate;
    private Gender gender;
    private String chatRoomUrl;
}
