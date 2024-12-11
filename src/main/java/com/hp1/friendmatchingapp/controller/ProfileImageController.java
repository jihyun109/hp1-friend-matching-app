package com.hp1.friendmatchingapp.controller;

import com.hp1.friendmatchingapp.service.ProfileImageService;
import com.hp1.friendmatchingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileImageService profileImageService;
    private final UserService userService;

}
