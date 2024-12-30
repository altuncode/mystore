package com.altuncode.mystore.model;

import com.altuncode.mystore.model.enums.ProductStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Product name is required and cannot be blank.")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters.")
    private String name;

    @Column(name = "url", unique = true)
    private String url;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "quantity", nullable = false)
    @Positive(message = "Quantity must be zero or positive.")
    @NotNull(message = "Quantity is required.")
    private Integer quantity;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be positive.")
    private Double price;

    @Column(name = "new_price")
    @Positive(message = "New price must be positive.")
    private Double newPrice;

    @Column(name = "width", nullable = false)
    @NotNull(message = "Width is required.")
    private Double width;

    @Column(name = "height", nullable = false)
    @NotNull(message = "Height is required.")
    private Double height;

    @Column(name = "depth", nullable = false)
    @NotNull(message = "Depth is required.")
    private Double depth;

    @NotNull(message = "Active status is required.")
    @Column(name = "active", nullable = false)
    private Boolean active=true;

    @ManyToOne
    @JoinColumn(name = "product_color_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ProductColor productColor;

    @ManyToOne
    @JoinColumn(name = "product_varian_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Variant productVariant;

    @ManyToMany
    @JoinTable(
            name = "product_sub_category_list",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "product_sub_category_id")
    )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<ProductSubCategory> productSubCategoryList;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "status_product", nullable = true)// This annotation maps the enum to a string in the database
    private ProductStatusEnum productStatusEnum;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate = LocalDate.now();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPdf> productPdfs = new ArrayList<>();

    public void addImage(ProductImage productImage) {
        images.add(productImage);
        productImage.setProduct(this);
    }

    public void removeImage(ProductImage productImage) {
        images.remove(productImage);
        productImage.setProduct(null);
    }

    public void addPdf(ProductPdf productPdf) {
        productPdfs.add(productPdf);
        productPdf.setProduct(this);
    }

    public void removePdf(ProductPdf productPdf) {
        productPdfs.remove(productPdf);
        productPdf.setProduct(null);
    }

    //check for thymlead that subcategories selected or not
    public boolean checkForMatchingId(int a) {
        if(productSubCategoryList==null)
            return false;
        for (ProductSubCategory b : productSubCategoryList) {
            if (b.getId() == a) {
                return true;
            }
        }
        return false;
    }


}