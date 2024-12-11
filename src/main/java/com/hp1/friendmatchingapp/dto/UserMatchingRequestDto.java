package com.hp1.friendmatchingapp.dto;

import com.hp1.friendmatchingapp.enums.Gender;
import com.hp1.friendmatchingapp.enums.Hobby;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserMatchingRequestDto {
    private String username;
    private Set<Gender> gender;
    private Set<Hobby> hobbies;
    private Set<Integer> ageRanges;
    private int pageNum;
    private int pageSize;
}
