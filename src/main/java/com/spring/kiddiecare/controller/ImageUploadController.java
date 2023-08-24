package com.spring.kiddiecare.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.spring.kiddiecare.domain.hospitalAdmin.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ImageUploadController {
    private final AmazonS3Client amazonS3Client;
    private final AdminRepository adminRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloudfront.cloud-front.url}")
    private String cloudUrl;

    private String s3Url;

    public String uploadFile(MultipartFile file, String adminName) {
        try {
            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket,adminName,file.getInputStream(),metadata);
            return "https://"+cloudUrl+"/"+adminName;

        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    public boolean deleteFile(String filePath){
        boolean isExistObject = amazonS3Client.doesObjectExist(bucket, filePath);
        if (isExistObject) {
            amazonS3Client.deleteObject(bucket, filePath);
            return true;
        }
        return false;
    }
}
