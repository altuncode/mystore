    package com.altuncode.mystore.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.NotNull;
    import jakarta.validation.constraints.Size;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.OnDelete;
    import org.hibernate.annotations.OnDeleteAction;

    import java.util.List;

    @Entity
    @Table(name = "product_sub_category")
    @Data
    @NoArgsConstructor
    public class ProductSubCategory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @NotBlank(message = "Name is required and cannot be blank.")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long.")
        @Column(name = "name", nullable = false, unique = true)
        private String name;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "category_id", referencedColumnName = "id")
        @OnDelete(action = OnDeleteAction.SET_NULL)
        private ProductCategory productCategory;

        @Column(name = "order_number", nullable = true)
        private Integer orderNumber;

        @NotNull(message = "Active status is required.")
        @Column(name = "active", nullable = false)
        private Boolean active=true;

        @NotNull(message = "Url is required.")
        @Column(name = "url", nullable = false, unique = true)
        private String url;

        @ManyToMany(mappedBy = "productSubCategoryList")
        private List<Product> productList;

    }