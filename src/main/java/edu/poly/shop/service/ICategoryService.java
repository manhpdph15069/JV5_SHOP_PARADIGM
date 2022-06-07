package edu.poly.shop.service;

import edu.poly.shop.entities._Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<_Category> findAll();

    _Category insert(_Category entity);

    _Category update(_Category entity);

    Optional<_Category> findById(Integer integer);

    _Category delete(Integer id);
}
