package com.hp1.friendmatchingapp.dto;

import com.hp1.friendmatchingapp.enums.Gender;
import com.hp1.friendmatchingapp.enums.Hobby;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserUpdateRequestDTO {
//    private MultipartFile profileImage;
    private String firstName;
    private String birthDate;
    private Gender gender;
    private String chatRoomUrl;
    @Builder.Default
    private Set<Hobby> hobbies = new HashSet<>();
}
