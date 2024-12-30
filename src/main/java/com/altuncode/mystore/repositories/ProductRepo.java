package com.altuncode.mystore.repositories;

import com.altuncode.mystore.model.Product;
import com.altuncode.mystore.repositories.projection.ProductListProjection;
import com.altuncode.mystore.repositories.projection.ProductProjectionUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("ProductRepo")
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p.id as id, p.name as name, p.price as price, p.newPrice as newPrice, " +
            "p.createdDate as createDate, p.url as url, (im.url,im.altText) as images, p.productStatusEnum as productStatusEnum " +
            "FROM Product p " +
            "LEFT JOIN p.images im "+
            "JOIN p.productSubCategoryList sc " +
            "WHERE sc.productCategory.url = :categoryUrl AND p.active = true ")
    Page<ProductProjectionUser> findActiveProductsByCategoryUrl(String categoryUrl, Pageable pageable);

    @Query("SELECT p.id as id, p.name as name, p.price as price, p.newPrice as newPrice, " +
            "p.createdDate as createDate, p.url as url, (im.url,im.altText) as images, p.productStatusEnum as productStatusEnum " +
            "FROM Product p " +
            "LEFT JOIN p.images im "+
            "JOIN p.productSubCategoryList sc " +
            "WHERE sc.productCategory.url = :categoryUrl AND sc.url = :subCategoryUrl AND p.active = true ")
    Page<ProductProjectionUser> findActiveProductsBySubCategoryUrl(String categoryUrl, String subCategoryUrl, Pageable pageable);

    @Query("SELECT p.id AS id, p.name AS name, p.price AS price, " +
            "p.newPrice AS newPrice, p.quantity AS quantity, " +
            "p.productColor.name AS productColorName, " +
            "p.active AS active, " +
            "STRING_AGG(s.name, ', ') AS productSubCategory " + // Use STRING_AGG for PostgreSQL
            "FROM Product p " +
            "LEFT JOIN p.productSubCategoryList s " +
            "GROUP BY p.id, p.name, p.price, p.newPrice, p.quantity, p.productColor.name, p.active " +
            "ORDER BY p.id DESC")
    Page<ProductListProjection> findAllProductList(Pageable pageable);

    Product findByUrlAndActiveTrue(String url);


}
