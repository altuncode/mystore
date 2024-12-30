package com.altuncode.mystore.repositories;

import com.altuncode.mystore.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("VariantRepo")
public interface VariantRepo extends JpaRepository<Variant, Long> {
}
