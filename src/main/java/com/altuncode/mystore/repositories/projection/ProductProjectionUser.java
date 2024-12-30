package com.altuncode.mystore.repositories.projection;

import com.altuncode.mystore.model.enums.ProductStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductProjectionUser {
    Long getId();
    String getName();
    String getUrl();
    Double getPrice();
    Double getNewPrice();
    List<ImageProjection> getImages();
    ProductStatusEnum getProductStatusEnum();
    LocalDateTime getCreateDate();

    interface ImageProjection {
        String getUrl();
        String getAltText();
    }
}
