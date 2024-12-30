package com.altuncode.mystore.controllers.user;

import com.altuncode.mystore.model.Product;
import com.altuncode.mystore.model.ProductCategory;
import com.altuncode.mystore.repositories.projection.ProductProjectionUser;
import com.altuncode.mystore.services.user.MainServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller("MainControllerUser")
@RequestMapping("/")
public class MainControllerUser {

    private final MainServiceUser mainServiceUser;

    @Autowired
    public MainControllerUser(@Qualifier("MainServiceUser") MainServiceUser mainServiceUser) {
        this.mainServiceUser = mainServiceUser;
    }

    @GetMapping()
    public String allGET(Model model) {
        List<ProductCategory> categories = mainServiceUser.getAllActiveCategories();
        model.addAttribute("categories", categories);
        return "user/index.html";
    }

    @GetMapping({"shop/{catName}", "shop/{catName}/"})
    public String getProductsByCategor(@PathVariable("catName") String categoryName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "createdDate") String sortField, Model model) {
        Sort sort;
        if (sortField.equals("ascprice")) {
            sort = Sort.by(Sort.Direction.ASC, "newPrice").and(Sort.by(Sort.Direction.ASC, "price"));
        } else if (sortField.equals("descprice")) {
            sort = Sort.by(Sort.Direction.DESC, "newPrice").and(Sort.by(Sort.Direction.DESC, "price"));
        } else {
            sort = Sort.by(Sort.Direction.DESC, "createdDate"); // Default sorting by latest
        }
        Page<ProductProjectionUser> products = mainServiceUser.getAllProductListByCategoryUrl(categoryName,PageRequest.of(page-1, 16, sort));
        model.addAttribute("products", products);
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", 16);
        model.addAttribute("currentUrl", categoryName);
        model.addAttribute("sortField", sortField);
        model.addAttribute("categories", mainServiceUser.getAllActiveCategories()); //default
        return "user/shop.html";
    }

    @GetMapping({"shop/{catName}/{subName}", "shop/{catName}/{subName}/"})
    public String getProductsBySubCategory2(@PathVariable("catName") String categoryName, @PathVariable("subName") String subCategoryName, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "createdDate") String sortField, Model model) {
        Sort sort;
        if (sortField.equals("ascprice")) {
            sort = Sort.by(Sort.Direction.ASC, "newPrice").and(Sort.by(Sort.Direction.ASC, "price"));
        } else if (sortField.equals("descprice")) {
            sort = Sort.by(Sort.Direction.DESC, "newPrice").and(Sort.by(Sort.Direction.DESC, "price"));
        } else {
            sort = Sort.by(Sort.Direction.DESC, "createdDate"); // Default sorting by latest
        }
        Page<ProductProjectionUser> products = mainServiceUser.getAllProductListBySubCategoryUrl(categoryName, subCategoryName, PageRequest.of(page-1, 16, sort));
        model.addAttribute("products", products);
        model.addAttribute("totalItems", products.getTotalElements());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", 16);
        model.addAttribute("currentUrl", categoryName+'/'+subCategoryName);
        model.addAttribute("sortField", sortField);
        model.addAttribute("categories", mainServiceUser.getAllActiveCategories()); //default
        return "user/shop.html";
    }

    @GetMapping({"product/{name}", "product/{name}/"})
    public String getProductByName(@PathVariable("name") String productUrl, Model model) {
        Product product = mainServiceUser.getOneProduct(productUrl);
        model.addAttribute("product", product);
        return "user/product.html";
    }


}





