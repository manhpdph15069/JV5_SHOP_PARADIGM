package edu.poly.shop.service.impl;

import edu.poly.shop.entities._Material;
import edu.poly.shop.repository.IMaterialRepository;
import edu.poly.shop.service.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MaterialService implements IMaterialService {
    @Autowired
    private IMaterialRepository repo;

    @Override
    public List<_Material> findAll(){
        return repo.findAll();
    }
}
