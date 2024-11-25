package com.hp1.friendmatchingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserScrollListResponseDTO {
    private Long id;
    private String userName;
    private String email;
}