package com.hp1.friendmatchingapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginResponseDto {
    private String token;
}