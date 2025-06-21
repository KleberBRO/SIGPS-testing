package com.ufrpe.sigps.repository;

import com.ufrpe.sigps.model.Brand;
import com.ufrpe.sigps.model.BrandType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findByBrandType(BrandType brandType);
    List<Brand> findByBrandNameContainingIgnoreCase(String brandName);
}