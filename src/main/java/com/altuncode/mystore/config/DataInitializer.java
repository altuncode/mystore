package com.altuncode.mystore.config;

import com.altuncode.mystore.model.*;
import com.altuncode.mystore.model.enums.ProductStatusEnum;
import com.altuncode.mystore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("DataInitializer")
public class DataInitializer implements CommandLineRunner {
    private final ProductColorRepo productColorRepo;
    private final ProductCategoryRepo productCategoryRepo;
    private final ProductSubCategoryRepo productSubCategoryRepo;
    private final ProductRepo productRepo;
    private final PersonRepo personRepo;

    @Autowired
    public DataInitializer(@Qualifier("PersonRepo") PersonRepo personRepo, @Qualifier("ProductCategoryRepo") ProductCategoryRepo productCategoryRepo, @Qualifier("ProductColorRepo") ProductColorRepo productColorRepo, @Qualifier("ProductRepo") ProductRepo productRepo, @Qualifier("ProductSubCategoryRepo") ProductSubCategoryRepo productSubCategoryRepo) {
        this.personRepo = personRepo;
        this.productCategoryRepo = productCategoryRepo;
        this.productColorRepo = productColorRepo;
        this.productRepo = productRepo;
        this.productSubCategoryRepo = productSubCategoryRepo;
    }


    @Override
    public void run(String... args) throws Exception {
        initializeProductColors();
        initializeProductCategory();
        initializeProductSubCategory();
        initializeProducts();

    }

    private void initializeProductColors() {
        if (productColorRepo.count() == 0) {
            ProductColor softCreamWhite = new ProductColor();
            softCreamWhite.setName("Soft Cream White");
            softCreamWhite.setHexCode("#FFFDFA");
            softCreamWhite.setOrderNumber(1);
            productColorRepo.save(softCreamWhite);

            ProductColor gray = new ProductColor();
            gray.setName("Gray");
            gray.setHexCode("#808080");
            gray.setOrderNumber(2);
            productColorRepo.save(gray);
        }
    }

    private void initializeProductCategory() {
        if (productCategoryRepo.count() == 0) {
            ProductCategory baseCabinet = new ProductCategory();
            baseCabinet.setName("Base Cabinet");
            baseCabinet.setUrl("base-cabinet");
            baseCabinet.setOrderNumber(1);
            productCategoryRepo.save(baseCabinet);

            ProductCategory tallCabinet = new ProductCategory();
            tallCabinet.setName("Tall Cabinet");
            tallCabinet.setUrl("tall-cabinet");
            tallCabinet.setOrderNumber(2);
            productCategoryRepo.save(tallCabinet);

            ProductCategory wallCabinet = new ProductCategory();
            wallCabinet.setName("Wall Cabinet");
            wallCabinet.setUrl("wall-cabinet");
            wallCabinet.setOrderNumber(3);
            productCategoryRepo.save(wallCabinet);

            ProductCategory complementary = new ProductCategory();
            complementary.setName("Component");
            complementary.setUrl("component");
            complementary.setOrderNumber(4);
            productCategoryRepo.save(complementary);
        }
    }

    private void initializeProductSubCategory() {
        if (productSubCategoryRepo.count() == 0) {
            ProductCategory baseCabinet = productCategoryRepo.findAll().get(0);
            ProductCategory tallCabinet = productCategoryRepo.findAll().get(1);
            ProductCategory wallCabinet = productCategoryRepo.findAll().get(2);
            ProductCategory complementary = productCategoryRepo.findAll().get(3);

            // Base Cabinet
            ProductSubCategory oneDrawer = new ProductSubCategory();
            oneDrawer.setName("Cabinet with One Drawer");
            oneDrawer.setOrderNumber(1);
            oneDrawer.setUrl("cabinet-with-one-drawer");
            oneDrawer.setProductCategory(baseCabinet);
            productSubCategoryRepo.save(oneDrawer);

            ProductSubCategory twoDrawers = new ProductSubCategory();
            twoDrawers.setName("Cabinet with Two Drawers");
            twoDrawers.setOrderNumber(2);
            twoDrawers.setUrl("cabinet-with-two-drawers");
            twoDrawers.setProductCategory(baseCabinet);
            productSubCategoryRepo.save(twoDrawers);

            ProductSubCategory threeDrawers = new ProductSubCategory();
            threeDrawers.setName("Cabinet with Three Drawers");
            threeDrawers.setOrderNumber(3);
            threeDrawers.setUrl("cabinet-with-three-drawers");
            threeDrawers.setProductCategory(baseCabinet);
            productSubCategoryRepo.save(threeDrawers);

            ProductSubCategory sinkBase = new ProductSubCategory();
            sinkBase.setName("Sink Base Cabinet");
            sinkBase.setOrderNumber(4);
            sinkBase.setUrl("sink-base-cabinet");
            sinkBase.setProductCategory(baseCabinet);
            productSubCategoryRepo.save(sinkBase);

            ProductSubCategory blindCorner = new ProductSubCategory();
            blindCorner.setName("Blind Corner Base Cabinet");
            blindCorner.setOrderNumber(5);
            blindCorner.setUrl("blind-corner-base-cabinet");
            blindCorner.setProductCategory(baseCabinet);
            productSubCategoryRepo.save(blindCorner);

            ProductSubCategory lCorner = new ProductSubCategory();
            lCorner.setName("L Corner Base Cabinet");
            lCorner.setOrderNumber(6);
            lCorner.setUrl("l-corner-base-cabinet");
            lCorner.setProductCategory(baseCabinet);
            productSubCategoryRepo.save(lCorner);

            // Tall Cabinet
            ProductSubCategory builtInOven = new ProductSubCategory();
            builtInOven.setName("Built-In Oven Tall Cabinet");
            builtInOven.setOrderNumber(1);
            builtInOven.setUrl("built-in-oven-tall-cabinet");
            builtInOven.setProductCategory(tallCabinet);
            productSubCategoryRepo.save(builtInOven);

            ProductSubCategory refrigeratorCabinet = new ProductSubCategory();
            refrigeratorCabinet.setName("Built-In Refrigerator Cabinet");
            refrigeratorCabinet.setOrderNumber(2);
            refrigeratorCabinet.setUrl("built-in-refrigerator-cabinet");
            refrigeratorCabinet.setProductCategory(tallCabinet);
            productSubCategoryRepo.save(refrigeratorCabinet);

            ProductSubCategory semiTallOven = new ProductSubCategory();
            semiTallOven.setName("Semi Tall Built-In Oven Cabinet");
            semiTallOven.setOrderNumber(3);
            semiTallOven.setUrl("semi-tall-built-in-oven-cabinet");
            semiTallOven.setProductCategory(tallCabinet);
            productSubCategoryRepo.save(semiTallOven);

            ProductSubCategory semiTallKitchen = new ProductSubCategory();
            semiTallKitchen.setName("Semi Tall Kitchen Cabinet");
            semiTallKitchen.setOrderNumber(4);
            semiTallKitchen.setUrl("semi-tall-kitchen-cabinet");
            semiTallKitchen.setProductCategory(tallCabinet);
            productSubCategoryRepo.save(semiTallKitchen);

            // Wall Cabinet
            ProductSubCategory blindCornerWall = new ProductSubCategory();
            blindCornerWall.setName("Blind Corner Wall Cabinet");
            blindCornerWall.setOrderNumber(1);
            blindCornerWall.setUrl("blind-corner-wall-cabinet");
            blindCornerWall.setProductCategory(wallCabinet);
            productSubCategoryRepo.save(blindCornerWall);

            ProductSubCategory lCornerWall = new ProductSubCategory();
            lCornerWall.setName("L Corner Wall Cabinet");
            lCornerWall.setOrderNumber(2);
            lCornerWall.setUrl("l-corner-wall-cabinet");
            lCornerWall.setProductCategory(wallCabinet);
            productSubCategoryRepo.save(lCornerWall);

            ProductSubCategory diagonalCornerWall = new ProductSubCategory();
            diagonalCornerWall.setName("Diagonal Corner Wall Cabinet");
            diagonalCornerWall.setOrderNumber(3);
            diagonalCornerWall.setUrl("diagonal-corner-wall-cabinet");
            diagonalCornerWall.setProductCategory(wallCabinet);
            productSubCategoryRepo.save(diagonalCornerWall);

            ProductSubCategory refrigeratorWall = new ProductSubCategory();
            refrigeratorWall.setName("Refrigerator Wall Cabinet");
            refrigeratorWall.setOrderNumber(4);
            refrigeratorWall.setUrl("refrigerator-wall-cabinet");
            refrigeratorWall.setProductCategory(wallCabinet);
            productSubCategoryRepo.save(refrigeratorWall);

            // Complementary
            ProductSubCategory baseSideCoverPanel = new ProductSubCategory();
            baseSideCoverPanel.setName("Base Side Cover Panel");
            baseSideCoverPanel.setOrderNumber(1);
            baseSideCoverPanel.setUrl("base-side-cover-panel");
            baseSideCoverPanel.setProductCategory(complementary);
            productSubCategoryRepo.save(baseSideCoverPanel);

            ProductSubCategory wallSideCoverPanel = new ProductSubCategory();
            wallSideCoverPanel.setName("Wall Side Cover Panel");
            wallSideCoverPanel.setOrderNumber(2);
            wallSideCoverPanel.setUrl("wall-side-cover-panel");
            wallSideCoverPanel.setProductCategory(complementary);
            productSubCategoryRepo.save(wallSideCoverPanel);

            ProductSubCategory semiTallSideCoverPanel = new ProductSubCategory();
            semiTallSideCoverPanel.setName("Semi-Tall Cabinet Side Cover Panel");
            semiTallSideCoverPanel.setOrderNumber(3);
            semiTallSideCoverPanel.setUrl("semi-tall-cabinet-side-cover-panel");
            semiTallSideCoverPanel.setProductCategory(complementary);
            productSubCategoryRepo.save(semiTallSideCoverPanel);

            ProductSubCategory tallSideCoverPanel = new ProductSubCategory();
            tallSideCoverPanel.setName("Tall Cabinet Side Cover Panel");
            tallSideCoverPanel.setOrderNumber(4);
            tallSideCoverPanel.setUrl("tall-cabinet-side-cover-panel");
            tallSideCoverPanel.setProductCategory(complementary);
            productSubCategoryRepo.save(tallSideCoverPanel);

            ProductSubCategory refrigeratorSideCoverPanel = new ProductSubCategory();
            refrigeratorSideCoverPanel.setName("Refrigerator Side Cover Panel");
            refrigeratorSideCoverPanel.setOrderNumber(5);
            refrigeratorSideCoverPanel.setUrl("refrigerator-side-cover-panel");
            refrigeratorSideCoverPanel.setProductCategory(complementary);
            productSubCategoryRepo.save(refrigeratorSideCoverPanel);

            ProductSubCategory canopyWallCoverPanel = new ProductSubCategory();
            canopyWallCoverPanel.setName("Canopy Wall Cover Panel");
            canopyWallCoverPanel.setOrderNumber(6);
            canopyWallCoverPanel.setUrl("canopy-wall-cover-panel");
            canopyWallCoverPanel.setProductCategory(complementary);
            productSubCategoryRepo.save(canopyWallCoverPanel);

            ProductSubCategory canopyUpperCoverPanel = new ProductSubCategory();
            canopyUpperCoverPanel.setName("Canopy Upper Cover Panel");
            canopyUpperCoverPanel.setOrderNumber(7);
            canopyUpperCoverPanel.setUrl("canopy-upper-cover-panel");
            canopyUpperCoverPanel.setProductCategory(complementary);
            productSubCategoryRepo.save(canopyUpperCoverPanel);
        }
    }

    private void initializeProducts() {
        if (productRepo.count() == 0) {
            List<ProductSubCategory> subCategories = productSubCategoryRepo.findAll();
            ProductColor defaultColor = productColorRepo.findAll().get(0); // Assuming the first color is the default

            for (ProductSubCategory subCategory : subCategories) {
                for (int i = 1; i <= 5; i++) {
                    Product product = new Product();
                    product.setName(subCategory.getName() + " Product " + i);
                    product.setDescription("Default description for " + subCategory.getName() + " Product " + i);
                    product.setShortDescription("Short description for " + subCategory.getName() + " Product " + i);
                    product.setQuantity(10 * i); // Example: Setting quantity to 10, 20, 30, ...
                    product.setPrice(50.0 * i); // Example: Setting price incrementally
                    product.setWidth(30.0);
                    product.setHeight(40.0);
                    product.setDepth(20.0);
                    product.setActive(true);
                    product.setProductColor(defaultColor);
                    product.setProductSubCategoryList(List.of(subCategory));
                    product.setProductStatusEnum(ProductStatusEnum.NEW); // Default status

                    productRepo.save(product);
                }
            }
        }
    }



}
