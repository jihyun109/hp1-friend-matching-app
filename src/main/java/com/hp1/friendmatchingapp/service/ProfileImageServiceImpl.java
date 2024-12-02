package com.hp1.friendmatchingapp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.hp1.friendmatchingapp.error.ErrorCode;
import com.hp1.friendmatchingapp.error.customExceptions.EmptyFileException;
import com.hp1.friendmatchingapp.error.customExceptions.IOExceiptionOnImageUploadException;
import com.hp1.friendmatchingapp.error.customExceptions.InvalidFileExtension;
import com.hp1.friendmatchingapp.error.customExceptions.MissingFileExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProfileImageServiceImpl implements ProfileImageService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public String uploadProfileImage(MultipartFile image) {
        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new EmptyFileException("Image file is empty.", ErrorCode.EMPTY_FILE);
        }
        return this.uploadImage(image);
    }

    @Override
    public void deleteProfileImage(int userId) {

    }

    private String uploadImage(MultipartFile image) {
        this.validateImageFileExtention(image.getOriginalFilename());
        try {
            return this.uploadImageToS3(image);
        } catch (IOException e) {
            throw new IOExceiptionOnImageUploadException(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD.getMessage(), ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD);
        }
    }

    private String uploadImageToS3(MultipartFile image) throws IOException {
        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try{
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest); // put image to S3
        }catch (Exception e){
            throw new IOExceiptionOnImageUploadException(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD.getMessage(), ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD);
        }finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new MissingFileExtensionException("The file is missing an extension. Please provide a valid file with an extension.", ErrorCode.MISSING_FILE_EXTENSION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extension)) {
            throw new InvalidFileExtension(ErrorCode.INVALID_FILE_EXTENSION.getMessage(), ErrorCode.INVALID_FILE_EXTENSION);
        }
    }
}
