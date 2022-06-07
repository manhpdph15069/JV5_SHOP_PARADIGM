package edu.poly.shop.repository;

import edu.poly.shop.entities._OrderDetaild;
import edu.poly.shop.entities._Orderdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IOrderDetailRepository extends JpaRepository<_Orderdetail, _OrderDetaild> {

}
