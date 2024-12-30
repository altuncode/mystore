package com.altuncode.mystore.repositories.projection;

import java.util.List;

public interface CategoryWithSubCategories {
    Long getId();

    String getName();

    List<SubCategoryDTO> getSubCategories();

    interface SubCategoryDTO {
        Long getId();
        String getName();
    }
}
