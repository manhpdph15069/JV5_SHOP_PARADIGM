package edu.poly.shop.repository;

import edu.poly.shop.entities._Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<_Category,Integer> {
}
