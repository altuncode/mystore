package com.altuncode.mystore.services.admin;

import com.altuncode.mystore.model.Product;
import com.altuncode.mystore.repositories.ProductRepo;
import com.altuncode.mystore.repositories.projection.ProductListProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


@Service("ProductService")
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepo productRepo;

    @Autowired
    public ProductService(@Qualifier("ProductRepo") ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    // List all products
    public Page<ProductListProjection> getAllProductList(Pageable pageable) {
        return productRepo.findAllProductList(pageable);
    }

    // Retrieve product by ID
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    //Save a product
    @Transactional
    public Product saveProduct(Product product) {
        Product savedProduct = productRepo.save(product);
        savedProduct.setUrl(generateUrl(savedProduct));
        return productRepo.save(product);
    }

    private String generateUrl(Product product) {
        String orginalName = product.getName();
        String newName = orginalName.trim().toLowerCase().replaceAll("[^a-z0-9-]", "-").replaceAll("-+", "-").replace("--","-");
        return newName + "-" + product.getId();
    }

}
