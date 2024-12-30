package com.altuncode.mystore.controllers.admin.product;

import com.altuncode.mystore.model.ProductSubCategory;
import com.altuncode.mystore.repositories.projection.ProductSubCategoryListProjection;
import com.altuncode.mystore.services.admin.ProductCategoryService;
import com.altuncode.mystore.services.admin.ProductSubCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("ProductSubCategoryController")
@RequestMapping("/admin/product/subcategory")
public class ProductSubCategoryController {

    private final ProductSubCategoryService productSubCategoryService;
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductSubCategoryController(@Qualifier("ProductSubCategoryService") ProductSubCategoryService productSubCategoryService, @Qualifier("ProductCategoryService") ProductCategoryService productCategoryService) {
        this.productSubCategoryService = productSubCategoryService;
        this.productCategoryService = productCategoryService;
    }

    // Get all subcategories with pagination
    @GetMapping({"list", "list/"})
    public String allGET(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<ProductSubCategoryListProjection> subCategories = productSubCategoryService.getAllProductSubCategories(PageRequest.of(page - 1, 10));
        model.addAttribute("subCategories", subCategories);
        model.addAttribute("totalItems", subCategories.getTotalElements());
        model.addAttribute("totalPage", subCategories.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", 10);
        return "admin/product/subcategory/list.html";
    }

    // Add new subcategory
    @GetMapping({"add", "add/"})
    public String addGET(Model model) {
        model.addAttribute("productSubCategory", new ProductSubCategory());
        model.addAttribute("productCategories", productCategoryService.getAllProductCategories()); // Fetch all categories
        return "admin/product/subcategory/addedit.html";
    }

    // Save new subcategory
    @PostMapping({"add", "add/"})
    public String addPOST(@ModelAttribute("productSubCategory") @Valid ProductSubCategory productSubCategory, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productCategories", productCategoryService.getAllProductCategories()); // Fetch all categories
            return "admin/product/subcategory/addedit.html";
        }
        productSubCategoryService.saveProductSubCategory(productSubCategory);
        return "redirect:/admin/product/subcategory/list";
    }

    // Edit subcategory
    @GetMapping({"edit/{id}", "edit/{id}/"})
    public String editGET(@PathVariable("id") Long id, Model model) {
        ProductSubCategory productSubCategory = productSubCategoryService.getProductSubCategoryById(id);
        if (productSubCategory == null) {
            return "redirect:/admin/product/subcategory/list";
        }
        model.addAttribute("productSubCategory", productSubCategory);
        model.addAttribute("productCategories", productCategoryService.getAllProductCategories()); // Fetch all categories
        return "admin/product/subcategory/addedit.html";
    }

    // Save edited subcategory
    @PatchMapping({"edit/{id}", "edit/{id}/"})
    public String editPATCH(@PathVariable("id") Long id, @ModelAttribute("productSubCategory") @Valid ProductSubCategory productSubCategory, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", productCategoryService.getAllProductCategories());
            return "admin/product/subcategory/addedit.html";
        }
        ProductSubCategory existingSubCategory = productSubCategoryService.getProductSubCategoryById(id);
        if (existingSubCategory == null) {
            return "redirect:/admin/product/subcategory/list";
        }
        productSubCategory.setId(id);
        productSubCategoryService.saveProductSubCategory(productSubCategory);
        return "redirect:/admin/product/subcategory/list";
    }

    // Delete subcategory
    @DeleteMapping({"delete/{id}", "delete/{id}/"})
    public String deleteDELETE(@PathVariable("id") Long id) {
        ProductSubCategory productSubCategory = productSubCategoryService.getProductSubCategoryById(id);
        if (productSubCategory == null) {
            return "redirect:/admin/product/subcategory/list";
        }
        productSubCategoryService.deleteProductSubCategoryById(id);
        return "redirect:/admin/product/subcategory/list";
    }
}
