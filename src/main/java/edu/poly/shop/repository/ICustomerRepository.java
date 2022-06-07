package edu.poly.shop.repository;

import edu.poly.shop.entities._Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<_Customer,Integer> {
}
