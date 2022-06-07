package edu.poly.shop.repository;


import edu.poly.shop.entities._Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFigureRepository extends JpaRepository<_Figure,Integer> {
}
