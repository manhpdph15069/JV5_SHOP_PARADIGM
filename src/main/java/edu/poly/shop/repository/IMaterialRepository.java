package edu.poly.shop.repository;

import edu.poly.shop.entities._Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMaterialRepository extends JpaRepository<_Material, Integer> {
}