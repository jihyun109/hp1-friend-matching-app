package com.hp1.friendmatchingapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
    String uploadProfileImage(MultipartFile image);
    void deleteProfileImage(int userId);
    void validateImageFileExtention(String filename);
}
