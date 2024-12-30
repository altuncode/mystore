package com.altuncode.mystore.services.user;

import com.altuncode.mystore.model.Product;
import com.altuncode.mystore.model.ProductCategory;
import com.altuncode.mystore.model.ProductSubCategory;
import com.altuncode.mystore.repositories.ProductCategoryRepo;
import com.altuncode.mystore.repositories.ProductRepo;
import com.altuncode.mystore.repositories.ProductSubCategoryRepo;
import com.altuncode.mystore.repositories.projection.ProductProjectionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("MainServiceUser")
@Transactional(readOnly = true)
public class MainServiceUser {

    //*************Varaibles***************//

    private final ProductCategoryRepo productCategoryRepo;
    private final ProductSubCategoryRepo productSubCategoryRepo;
    private final ProductRepo productRepoUser;


    //*************Constructor***************//

    @Autowired
    public MainServiceUser(@Qualifier("ProductCategoryRepo") ProductCategoryRepo productCategoryRepo, @Qualifier("ProductSubCategoryRepo") ProductSubCategoryRepo productSubCategoryRepo, @Qualifier("ProductRepo") ProductRepo productRepoUser) {
        this.productCategoryRepo = productCategoryRepo;
        this.productSubCategoryRepo = productSubCategoryRepo;
        this.productRepoUser = productRepoUser;
    }

    //*************Categories***************//

    //get all list of active categories by ordered orderNumber asc
    public List<ProductCategory> getAllActiveCategories() {
        return productCategoryRepo.findByActiveTrueOrderByOrderNumberAsc();
    }

    //get one category by id
    public ProductCategory getCategoryById(Long id) {
        return productCategoryRepo.findById(id).orElse(null);
    }

    //*************Subcategory***************//

    //get all list of active subcategories by ordered orderNumber asc
    public List<ProductSubCategory> getSubCategoriesForCategory(ProductCategory category) {
        return productSubCategoryRepo.findByProductCategoryAndActiveTrueOrderByOrderNumberAsc(category);
    }

    //get one subcategory by id
    public ProductSubCategory getSubCategoryById(Long id) {
        return productSubCategoryRepo.findById(id).orElse(null);
    }

    //*************Products***************//

    //get one product by name
    public Product getOneProduct(String name) {
        return productRepoUser.findByUrlAndActiveTrue(name);
    }

    //get all product related with categoryID by ordered created asc
    public Page<ProductProjectionUser> getAllProductListByCategoryUrl(String categoryUrl, Pageable pageable) {
        return productRepoUser.findActiveProductsByCategoryUrl(categoryUrl,pageable);
    }

    //get all product related with subcategoryId by ordered created asc
    public Page<ProductProjectionUser> getAllProductListBySubCategoryUrl(String categoryUrl, String subCategoryUrl, Pageable pageable) {
        return productRepoUser.findActiveProductsBySubCategoryUrl(categoryUrl, subCategoryUrl, pageable);
    }


}
