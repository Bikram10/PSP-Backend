package com.bikram.appliedproject.configuration;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class cloudinaryConfig {
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dwoyu3cxt",
            "api_key", "788331915557946",
            "api_secret", "UHJa3VB0_LjcY7Ydk7-jBgD5RbI"));

    public Cloudinary getCloudinary() {
        return cloudinary;
    }
}
