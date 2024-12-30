package com.altuncode.mystore.repositories.projection;

import java.util.List;

// For to list product with these fields
public interface ProductListProjection {
    Long getId();
    String getName();
    int getQuantity();
    Double getPrice();
    Double getNewPrice();
    Boolean getActive();
    String getProductColorName();
    List<String> getProductSubCategory();

}
