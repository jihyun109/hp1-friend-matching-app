package com.hp1.friendmatchingapp.controller;

import com.hp1.friendmatchingapp.service.ProfileImageService;
import com.hp1.friendmatchingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileImageService profileImageService;
    private final UserService userService;
    @PutMapping("/image/{userId}")
    public ResponseEntity<String> editeProfileImage(@PathVariable("userId") Long userId, @RequestPart(value= "image") MultipartFile image) {
        String uploadedUrl = profileImageService.uploadProfileImage(image);
        userService.updateProfileImage(userId, uploadedUrl);
        return ResponseEntity.ok("프로필 사진 수정 완료");
    }
}
