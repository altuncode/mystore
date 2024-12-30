package com.altuncode.mystore.controllers.admin.product;

import com.altuncode.mystore.model.Product;
//import com.altuncode.mystore.model.ProductImage;
import com.altuncode.mystore.model.ProductImage;
import com.altuncode.mystore.model.ProductPdf;
import com.altuncode.mystore.model.ProductSubCategory;
import com.altuncode.mystore.model.enums.ProductStatusEnum;
import com.altuncode.mystore.repositories.projection.ProductListProjection;
import com.altuncode.mystore.services.admin.*;
import com.altuncode.mystore.util.validator.ProductFileValidator;
import com.altuncode.mystore.util.validator.ProductValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller("ProductController")
@RequestMapping("/admin/product/")
public class ProductController {

    private final ProductService productService;
    private final ProductColorService productColorService;
    private final ProductSubCategoryService productSubCategoryService;
    private final ProductCategoryService productCategoryService;
    private final ProductImageService productImageService;
    private final ProductPdfService productPdfService;
    private final ProductValidator productValidator;
    private final ProductFileValidator productFileValidator;

    @Autowired
    public ProductController(@Qualifier("ProductService") ProductService productService, @Qualifier("ProductColorService") ProductColorService productColorService, @Qualifier("ProductSubCategoryService") ProductSubCategoryService productSubCategoryService, @Qualifier("ProductCategoryService") ProductCategoryService productCategoryService, @Qualifier("ProductImageService") ProductImageService productImageService, @Qualifier("ProductPdfService") ProductPdfService productPdfService, @Qualifier("ProductValidator") ProductValidator productValidator, @Qualifier("ProductFileValidator") ProductFileValidator productFileValidator) {
        this.productService = productService;
        this.productColorService = productColorService;
        this.productSubCategoryService = productSubCategoryService;
        this.productCategoryService = productCategoryService;
        this.productImageService = productImageService;
        this.productPdfService = productPdfService;
        this.productValidator = productValidator;
        this.productFileValidator = productFileValidator;
    }

    // Get all products
    @GetMapping({"list", "list/"})
    public String allGET(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<ProductListProjection> products = productService.getAllProductList(PageRequest.of(page-1, 10));
        model.addAttribute("products", products);
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", 10);
        return "admin/product/list.html";
    }

    // get one product
    @GetMapping({"/{id}", "/{id}/"})
    public String oneProductGET(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/admin/product/list"; // Redirect if product not found
        }
        model.addAttribute("product", product);
        return "admin/product/oneProduct.html";
    }

    // Add new product
    @GetMapping({"add", "add/"})
    public String addGET(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productCategoryService.getAllCategoriesWithSubCategories());
        model.addAttribute("colors", productColorService.findAll());
        model.addAttribute("statuses", ProductStatusEnum.values());
        return "admin/product/addedit.html";
    }

    //Save new product
    @PostMapping({"add","add/"})
    public String addPOST( @ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam(value = "imagess", required = false) List<MultipartFile> images, @RequestParam(value = "pdfs", required = false) List<MultipartFile> pdfs, @RequestParam(value = "subCategoryIds", required = false) List<Long> subCategoryIds, Model model) {

        //check validation
        productValidator.validate(product, bindingResult);
        productFileValidator.setProfuctFiles(new ProductImage());
        productFileValidator.validate(images, bindingResult);
        productFileValidator.setProfuctFiles(new ProductPdf());
        productFileValidator.validate(pdfs, bindingResult);


        if(subCategoryIds != null && subCategoryIds.size() > 0) {
            List<ProductSubCategory> subCategories = productSubCategoryService.findSubCategoriesByIds(subCategoryIds);
            product.setProductSubCategoryList(subCategories);
        }else {
            model.addAttribute("catCheck", true);
        }

        if(bindingResult.hasErrors() || model.containsAttribute("catCheck")) {
            model.addAttribute("statuses", ProductStatusEnum.values());
            model.addAttribute("categories", productCategoryService.getAllCategoriesWithSubCategories());
            model.addAttribute("colors", productColorService.findAll());
            return "admin/product/addedit.html";
        }

        try {
            // Save product first to generate ID
            Product savedProduct = productService.saveProduct(product);

            // Save each image and associate with the book
            if (images != null) {
                for (MultipartFile file : images) {
                    if (!file.isEmpty()) {
                        ProductImage image = productImageService.saveImage(file);
                        savedProduct.addImage(image);
                    }
                }
            }
            // Save each pdf and associate with the book
            if (pdfs != null) {
                for (MultipartFile pdf : pdfs) {
                    if (!pdf.isEmpty()) {
                        ProductPdf productPdf = productPdfService.savePdf(pdf);
                        savedProduct.addPdf(productPdf);
                    }
                }
            }
            // Save book again with images
            productService.saveProduct(savedProduct);
            return "redirect:/admin/product/list";
        } catch (IOException e) {
            e.printStackTrace();
            bindingResult.rejectValue("images", "error.images",
                    "An error occurred while uploading files.");
            return "redirect:/admin/product/color/list";
        }
    }

    // Update product - Display the edit form
    @GetMapping({"edit/{id}", "edit/{id}/"})
    public String editGET(@PathVariable("id") Long id, Model model) {
        // Fetch the product by ID
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/admin/product/list"; // Redirect if product not found
        }
        // Populate model attributes
        model.addAttribute("product", product);
        model.addAttribute("categories", productCategoryService.getAllCategoriesWithSubCategories());
        model.addAttribute("colors", productColorService.findAll());
        model.addAttribute("statuses", ProductStatusEnum.values());
        return "admin/product/addedit.html";
    }

    // Update existing product
    @PatchMapping({"edit/{id}", "edit/{id}/"})
    public String updatePOST(@PathVariable("id") Long id,
                             @ModelAttribute("product") @Valid Product product,
                             BindingResult bindingResult,
                             @RequestParam(value = "imagess", required = false) List<MultipartFile> images,
                             @RequestParam(value = "pdfs", required = false) List<MultipartFile> pdfs,
                             @RequestParam(value = "deletedImg", required = false) List<Long> deletedImgIds,
                             @RequestParam(value = "deletedPdf", required = false) List<Long> deletePdf,
                             @RequestParam(value = "subCategoryIds", required = false) List<Long> subCategoryIds,
                             Model model) {
        //check validation
        productValidator.validate(product, bindingResult);
        productFileValidator.setProfuctFiles(new ProductImage());
        productFileValidator.validate(images, bindingResult);
        productFileValidator.setProfuctFiles(new ProductPdf());
        productFileValidator.validate(pdfs, bindingResult);

        if (subCategoryIds != null && !subCategoryIds.isEmpty()) {
            List<ProductSubCategory> subCategories = productSubCategoryService.findSubCategoriesByIds(subCategoryIds);
            product.setProductSubCategoryList(subCategories);
        } else {
            model.addAttribute("catCheck", true);
        }


        if (bindingResult.hasErrors() || model.containsAttribute("catCheck")) {
            model.addAttribute("categories", productCategoryService.getAllCategoriesWithSubCategories());
            model.addAttribute("colors", productColorService.findAll());
            model.addAttribute("statuses", ProductStatusEnum.values());
            return "admin/product/addedit.html";
        }
        try {
            // Fetch existing product from the database
            Product existingProduct = productService.getProductById(id);
            if (existingProduct == null) {
                return "redirect:/admin/product/list"; // Redirect if product not found
            }
            // Update the product details
            existingProduct.setName(product.getName());
            existingProduct.setShortDescription(product.getShortDescription());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setWidth(product.getWidth());
            existingProduct.setHeight(product.getHeight());
            existingProduct.setDepth(product.getDepth());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setNewPrice(product.getNewPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setActive(product.getActive());
            existingProduct.setProductColor(product.getProductColor());
            existingProduct.setProductSubCategoryList(product.getProductSubCategoryList());
            existingProduct.setProductStatusEnum(product.getProductStatusEnum());


            //hange update img
            if(!product.getProductPdfs().isEmpty()){
                for(ProductPdf pdf: product.getProductPdfs()){
                    productPdfService.updatePdf(pdf.getId(),pdf.getAltText(),pdf.getOrderPdf());
                }
            }


            // Handle deleted pdf
            if (deletePdf != null) {
                for (Long pdfId : deletePdf) {
                    productPdfService.deletePdf(pdfId);
                }
            }

            // Handle new pdf
            if (pdfs != null) {
                for (MultipartFile pdf : pdfs) {
                    if (!pdf.isEmpty()) {
                        ProductPdf productPdf = productPdfService.savePdf(pdf);
                        existingProduct.addPdf(productPdf);
                    }
                }
            }


            //hange update img
            if(!product.getImages().isEmpty()){
                for(ProductImage image: product.getImages()){
                    productImageService.updateImageDetails(image.getId(),image.getAltText(),image.getOrderImg());
                }
            }

            // Handle deleted images
            if (deletedImgIds != null) {
                for (Long imgId : deletedImgIds) {
                    productImageService.deleteImage(imgId);
                }
            }
            // Handle new images
            if (images != null) {
                for (MultipartFile file : images) {
                    if (!file.isEmpty()) {
                        ProductImage image = productImageService.saveImage(file);
                        existingProduct.addImage(image);
                    }
                }
            }
            // Save updated product
            productService.saveProduct(existingProduct);
            return "redirect:/admin/product/list";
        } catch (IOException e) {
            e.printStackTrace();
            bindingResult.rejectValue("images", "error.images",
                    "An error occurred while uploading files.");
            return "admin/product/addedit.html";
        }
    }


}
