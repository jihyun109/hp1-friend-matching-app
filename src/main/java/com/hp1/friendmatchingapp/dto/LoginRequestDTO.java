package com.hp1.friendmatchingapp.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoginRequestDTO {
    private String username;
    private String password;
    private String code;
}
