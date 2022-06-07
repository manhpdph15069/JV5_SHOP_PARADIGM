package edu.poly.shop.service.impl;

import edu.poly.shop.entities._Figure;
import edu.poly.shop.repository.IFigureRepository;
import edu.poly.shop.service.IFigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FigureService implements IFigureService {
    @Autowired
    private IFigureRepository figureRepository;

    @Override
    public List<_Figure> findAll() {
        return figureRepository.findAll();
    }

    @Override
    public _Figure insert(_Figure entity) {
        entity.setId(null);
        return figureRepository.save(entity);
    }

    @Override
    public Optional<_Figure> findById(Integer integer) {
        return figureRepository.findById(integer);
    }

    @Override
    public _Figure update(_Figure entity) {
        if (entity.getId() != null) {
            Optional<_Figure> p = figureRepository.findById(entity.getId());
            if (p.isPresent()) {
                return figureRepository.save(entity);
            }
        }
        return null;
    }

    @Override
    public _Figure delete(Integer id) {
        if (id != null) {
            Optional<_Figure> p = figureRepository.findById(id);
            if (p.isPresent()) {
                figureRepository.deleteById(id);
                return p.get();
            }
        }
        return null;
    }
}
