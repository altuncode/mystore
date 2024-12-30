package com.altuncode.mystore.util.validator;
import com.altuncode.mystore.model.ProductImage;
import com.altuncode.mystore.model.interfaces.IProfuctFiles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component("ProductFileValidator")
@Data
@NoArgsConstructor
public class ProductFileValidator implements Validator {

    private IProfuctFiles profuctFiles;

    @Override
    public boolean supports(Class<?> clazz) {
        return IProfuctFiles.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!(target instanceof List)) {
            errors.reject("invalid.target", "Target must be a list of MultipartFile objects.");
            return;
        }

        @SuppressWarnings("unchecked")
        List<MultipartFile> files = (List<MultipartFile>) target;

        if (files != null) {
            List<String> ALLOWED_EXTENSIONS = profuctFiles.getAllowedExtensions();
            List<String> ALLOWED_CONTENT_TYPES = profuctFiles.getAllowedContentTypes();
            String errorField = profuctFiles instanceof ProductImage ? "images" : "files";
            String errorCode = profuctFiles instanceof ProductImage ? "error.images" : "error.files";

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String extension = getFileExtension(file.getOriginalFilename());
                    String contentType = file.getContentType();

                    if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase()) || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
                        errors.rejectValue(errorField, errorCode, "Invalid file type for file: " + file.getOriginalFilename() + ". Only " + ALLOWED_EXTENSIONS.toString() + " are allowed.");
                    }
                }
            }
        }

    }

    public String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }





}
