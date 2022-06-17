package edu.poly.shop.repository;

import edu.poly.shop.entities._Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMaterialRepository extends JpaRepository<_Material, Integer> {
}