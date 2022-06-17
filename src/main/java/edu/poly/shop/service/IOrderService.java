package edu.poly.shop.service;

import edu.poly.shop.beans.__CustomerModel;
import edu.poly.shop.entities._Order;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    String add(__CustomerModel customerModel, RedirectAttributes redirectAttributes);

    Page<_Order> findAllPage(int pageNumber, int maxRecord);

    _Order done(Integer id);

    _Order confirm(Integer id);

    _Order cancel(Integer id);

    List<_Order> findAll();

    Optional<_Order> findById(Integer integer);
}
