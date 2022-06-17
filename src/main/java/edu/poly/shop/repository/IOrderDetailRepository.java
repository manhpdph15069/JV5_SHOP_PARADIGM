package edu.poly.shop.repository;

import edu.poly.shop.entities._OrderDetaild;
import edu.poly.shop.entities._Orderdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IOrderDetailRepository extends JpaRepository<_Orderdetail, Integer> {
    @Query("select  p.id,p.paradigmName,count(o.paradigm) from\n" +
            " _Orderdetail o inner join  _Paradigm p on o.paradigm=p.id\n" +
            "group by o.paradigm\n" +
            "order by count(o.paradigm) desc")
    public List<Object[]> banChay();

    @Query("select p.id,p.paradigmName,p.price * od.purchasedQuantity\n" +
            "from _Order o\n" +
            "         join _Orderdetail od on od.order = o.id\n" +
            "         join _Paradigm p on od.paradigm = p.id\n" +
            "group by od.paradigm\n" +
            "order by p.price * od.purchasedQuantity desc ")
    public List<Object[]> doanhThu();
}
