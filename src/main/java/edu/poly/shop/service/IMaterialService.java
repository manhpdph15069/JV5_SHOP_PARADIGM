package edu.poly.shop.service;

import edu.poly.shop.entities._Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMaterialService{
    List<_Material> findAll();
}
