package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.product.ImageUrls;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<ImageUrls, Long> {
    ImageUrls findByUrl(String url);
}
