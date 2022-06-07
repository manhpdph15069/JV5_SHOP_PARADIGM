package edu.poly.shop.service;

import edu.poly.shop.entities._Figure;

import java.util.List;
import java.util.Optional;

public interface IFigureService {
    List<_Figure> findAll();

    _Figure insert(_Figure entity);

    Optional<_Figure> findById(Integer integer);

    _Figure update(_Figure entity);

    _Figure delete(Integer id);
}
