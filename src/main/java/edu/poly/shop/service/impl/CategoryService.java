package edu.poly.shop.service.impl;

import edu.poly.shop.entities._Category;
import edu.poly.shop.repository.ICategoryRepository;
import edu.poly.shop.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<_Category> findAll() {
        return categoryRepository.findAll();
    }


    @Override
    public _Category insert(_Category entity) {
        entity.setId(null);
        return categoryRepository.save(entity);
    }



    @Override
    public _Category update(_Category entity) {
        if (entity.getId() != null) {
            Optional<_Category> p = categoryRepository.findById(entity.getId());
            if (p.isPresent()) {
                return categoryRepository.save(entity);
            }
        }
        return null;
    }

    @Override
    public Optional<_Category> findById(Integer integer) {
        return categoryRepository.findById(integer);
    }

    @Override
    public _Category delete(Integer id) {
        if (id != null) {
            Optional<_Category> p = categoryRepository.findById(id);
            if (p.isPresent()) {
                categoryRepository.deleteById(id);
                return p.get();
            }
        }
        return null;
    }
}
