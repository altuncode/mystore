package com.altuncode.mystore.services.admin;

import com.altuncode.mystore.model.ProductCategory;
import com.altuncode.mystore.repositories.ProductCategoryRepo;
import com.altuncode.mystore.repositories.projection.CategoryWithSubCategories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("ProductCategoryService")
@Transactional(readOnly = true)
public class ProductCategoryService {

    private final ProductCategoryRepo productCategoryRepo;

    @Autowired
    public ProductCategoryService(@Qualifier("ProductCategoryRepo") ProductCategoryRepo productCategoryRepo) {
        this.productCategoryRepo = productCategoryRepo;
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepo.findAll();
    }

    public Page<ProductCategory> getAllProductCategories(Pageable pageable) {
        return productCategoryRepo.findAllOrderedByIdDesc(pageable);
    }

    public ProductCategory getProductCategoryById(Long id) {
        Optional<ProductCategory> productCategory = productCategoryRepo.findById(id);
        return productCategory.orElse(null);
    }

    public List<CategoryWithSubCategories> getAllCategoriesWithSubCategories() {
        return productCategoryRepo.findAllCategoriesWithSubCategories();
    }

    @Transactional
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return productCategoryRepo.save(productCategory);
    }

    @Transactional
    public ProductCategory updateProductCategory(ProductCategory productCategory) {
        return productCategoryRepo.save(productCategory);
    }

    @Transactional
    public void deleteProductCategoryById(Long id) {
        productCategoryRepo.deleteById(id);
    }
}
