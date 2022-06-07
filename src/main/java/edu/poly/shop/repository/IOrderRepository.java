package edu.poly.shop.repository;

import edu.poly.shop.entities._Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<_Order, Integer> {

}
