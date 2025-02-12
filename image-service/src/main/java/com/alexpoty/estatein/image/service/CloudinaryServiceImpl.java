package com.alexpoty.estatein.image.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class CloudinaryServiceImpl implements CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            var uploadedUrl = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadedUrl.get("url").toString();
        } catch (IOException e) {
            return null;
        }
    }
}
