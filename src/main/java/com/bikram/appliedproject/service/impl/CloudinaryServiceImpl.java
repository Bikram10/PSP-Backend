package com.bikram.appliedproject.service.impl;

import com.bikram.appliedproject.configuration.cloudinaryConfig;
import com.bikram.appliedproject.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private cloudinaryConfig cloudinaryConfig;

    @Override
    public String upload(MultipartFile multipartFile, String filename) throws IOException {
        Map params = ObjectUtils.asMap(
                "public_id", filename,
                "overwrite", true,
                "resource_type", "auto",
                "invalidate", true
        );

        File file = new File(multipartFile.getOriginalFilename());
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);

        fos.write(multipartFile.getBytes());
        fos.close();

        Cloudinary cloudinary = cloudinaryConfig.getCloudinary();
        Map uploadResult = cloudinary.uploader().upload(file, params);
        file.delete();
        return uploadResult.get("public_id").toString();
    }

    @Override
    public void remove(String filepath) throws IOException {
        Cloudinary cloudinary = cloudinaryConfig.getCloudinary();
        cloudinary.uploader().destroy(filepath, ObjectUtils.emptyMap());
    }
}
