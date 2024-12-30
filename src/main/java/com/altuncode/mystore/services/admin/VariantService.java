package com.altuncode.mystore.services.admin;

import com.altuncode.mystore.model.Variant;
import com.altuncode.mystore.repositories.VariantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("VariantService")
@Transactional(readOnly = true)
public class VariantService {

    private final VariantRepo variantRepo;


    @Autowired
    public VariantService(@Qualifier("VariantRepo") VariantRepo variantRepo) {
        this.variantRepo = variantRepo;
    }

    // get all color list without pageble
    public List<Variant> findAll() {
        return variantRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Page<Variant> getAll(Pageable pageable) {
        return variantRepo.findAll(pageable);
    }

    public Optional<Variant> findOne(Long id) {
        return variantRepo.findById(id);
    }

    @Transactional
    public Variant saveVariant(Variant variant) {
        return variantRepo.save(variant);
    }

    @Transactional
    public Variant updateVariant(Variant variant) {
        return variantRepo.save(variant);
    }

    @Transactional
    public void deleteVariantById(Long id) {
        variantRepo.deleteById(id);
    }
}
