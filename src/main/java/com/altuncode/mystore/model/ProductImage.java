package com.altuncode.mystore.model;

import com.altuncode.mystore.model.interfaces.IProfuctFiles;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;


@Entity
@Table(name = "product_image")
@Data
@NoArgsConstructor
public class ProductImage implements IProfuctFiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_img", nullable = true)
    private Integer orderImg;

    @Column(name = "alt_text", nullable = true)
    private String altText;

    @Column(name = "url", nullable = false,unique = true)
    private String url;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Override
    public List<String> getAllowedExtensions() {
        return Arrays.asList("jpg", "jpeg", "png", "webp");
    }

    @Override
    public List<String> getAllowedContentTypes() {
        return Arrays.asList("image/jpeg", "image/png", "image/webp");
    }
}
