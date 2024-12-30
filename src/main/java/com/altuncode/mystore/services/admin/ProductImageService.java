package com.altuncode.mystore.services.admin;

import com.altuncode.mystore.model.ProductImage;
import com.altuncode.mystore.repositories.ProductImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.nio.file.*;

@Service("ProductImageService")
public class ProductImageService {
    private final ProductImageRepo productImageRepo;


    @Value("${file.images-dir}")
    private String uploadDir;

    // Allowed file types
    public static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "webp");
    public static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/webp");

    @Autowired
    public ProductImageService(@Qualifier("ProductImageRepo") ProductImageRepo productImageRepo) {
        this.productImageRepo = productImageRepo;
    }

    public List<ProductImage> getImagesForBook(Long productId) {
        return productImageRepo.findByProductId(productId); // Add filtering by bookId if needed
    }


    // this method for save image
    @Transactional
    public ProductImage saveImage(MultipartFile file) throws IOException {
        // Get current year and month
        LocalDate currentDate = LocalDate.now();
        String year = String.valueOf(currentDate.getYear());
        String month = String.format("%02d", currentDate.getMonthValue());

        // Generate filename
        String randomName = generateCustomString();
        String extension = getFileExtension(file.getOriginalFilename());
        String filename = randomName + "." + extension;

        //product name in first
        //String filename = productName.toLowerCase().replace(" ", "-") + "-" + randomName + "." + extension;

        // Define the path: uploads/year/month/
        Path uploadPath = Paths.get(uploadDir, year, month).toAbsolutePath().normalize();

        // Ensure directories exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save file to disk
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Create Image entity with URL pointing to /uploads/{year}/{month}/filename.ext
        ProductImage image = new ProductImage();
        image.setUrl("/" + "images" + "/" + year + "/" + month + "/" + filename);
        return productImageRepo.save(image);
    }

    // Delete image by ID
    @Transactional
    public void deleteImage(Long id) throws IOException {
        Optional<ProductImage> optionalImage = productImageRepo.findById(id);
        if (optionalImage.isPresent()) {
            ProductImage image = optionalImage.get();
            // Delete file from disk
            String imageUrl = image.getUrl(); // e.g., /uploads/2024/09/filename.jpg
            Path filePath = Paths.get(uploadDir).resolve(imageUrl.replace("/images/", ""));
            Files.deleteIfExists(filePath);
            // Delete from DB
            productImageRepo.delete(image);
        }
    }

    //this method for update
    @Transactional
    public void updateImageDetails(Long id, String altText, Integer orderImg) {
        Optional<ProductImage> image = productImageRepo.findById(id);
        if(image.isPresent()){
            image.get().setAltText(altText);
            image.get().setOrderImg(orderImg);
            productImageRepo.save(image.get());
        }

    }

    // This method only for create name
    public static String generateCustomString() {
        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

        //Random words
        String randomUUID = UUID.randomUUID().toString().replace("-", "").substring(0, 8);


        String date = now.format(dateFormatter);
        String time = now.format(timeFormatter);

        // Get timezone offset in hours
        ZoneOffset offset = now.getOffset();
        int totalSeconds = offset.getTotalSeconds();
        int hoursOffset = totalSeconds / 3600;


        String offsetStr;
        if (hoursOffset > 0) {
            offsetStr = "p" + hoursOffset;
        } else if (hoursOffset < 0) {
            offsetStr = "m" + Math.abs(hoursOffset);
        } else {
            offsetStr = "e";
        }

        // Get nanoseconds, padded to 9 digits
        int nano = now.getNano();
        String nanoStr = String.format("%09d", nano);

        // Generate a random 6-digit number, padded with leading zeros if necessary
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // 0 to 9999
        String randomStr = String.format("%04d", randomNumber);

        // Combine all parts into the final string
        String result = date + time + "tz" + offsetStr + nanoStr + randomUUID +randomStr;

        return result;
    }

    // Helper method to extract file extension
    public String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

}
