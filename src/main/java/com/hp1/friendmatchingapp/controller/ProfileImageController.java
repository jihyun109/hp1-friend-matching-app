package com.hp1.friendmatchingapp.controller;

import com.hp1.friendmatchingapp.service.ProfileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class ProfileImageController {
    private final ProfileImageService profileImageService;
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestPart(value= "image") MultipartFile image) {
        return ResponseEntity.ok("S3에 사진 업로드 완료. url: " + profileImageService.uploadProfileImage(image));
    }
}
