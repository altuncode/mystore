package com.altuncode.mystore.controllers.admin.product;

import com.altuncode.mystore.model.ProductCategory;
import com.altuncode.mystore.services.admin.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("ProductCategoryController")
@RequestMapping("/admin/product/category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(@Qualifier("ProductCategoryService") ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    // Get all categories
    @GetMapping({"list", "list/"})
    public String allGET(Model model, @RequestParam(defaultValue = "1") int page) {
        Page<ProductCategory> productCategories = productCategoryService.getAllProductCategories(PageRequest.of(page - 1, 10));
        model.addAttribute("productCategories", productCategories);
        model.addAttribute("totalItems", productCategories.getTotalElements());
        model.addAttribute("totalPage", productCategories.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", 10);
        return "admin/product/category/list.html";
    }

    // Add new category
    @GetMapping({"add", "add/"})
    public String addGET(Model model) {
        model.addAttribute("productCategory", new ProductCategory());
        return "admin/product/category/addedit.html";
    }

    // Save new category
    @PostMapping({"add", "add/"})
    public String addPOST(@ModelAttribute("productCategory") @Valid ProductCategory productCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/product/category/addedit.html";
        }
        productCategoryService.saveProductCategory(productCategory);
        return "redirect:/admin/product/category/list";
    }

    // Edit category
    @GetMapping({"edit/{id}", "edit/{id}/"})
    public String editGET(@PathVariable("id") Long id, Model model) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
        if (productCategory == null) {
            return "redirect:/admin/product/category/list";
        }
        model.addAttribute("productCategory", productCategory);
        return "admin/product/category/addedit.html";
    }

    // Save edited category
    @PatchMapping({"edit/{id}", "edit/{id}/"})
    public String editPATCH(@PathVariable("id") Long id, @ModelAttribute("productCategory") @Valid ProductCategory productCategory, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/product/category/addedit.html";
        }
        if (productCategoryService.getProductCategoryById(id) == null) {
            return "redirect:/admin/product/category/list";
        }
        productCategory.setId(id);
        productCategoryService.updateProductCategory(productCategory);
        return "redirect:/admin/product/category/list";
    }

    // Delete category
    @DeleteMapping({"delete/{id}", "delete/{id}/"})
    public String deleteDELETE(@PathVariable("id") Long id) {
        if (productCategoryService.getProductCategoryById(id) == null) {
            return "redirect:/admin/product/category/list";
        }
        productCategoryService.deleteProductCategoryById(id);
        return "redirect:/admin/product/category/list";
    }

}
