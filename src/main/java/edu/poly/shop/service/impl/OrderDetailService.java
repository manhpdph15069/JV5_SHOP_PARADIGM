package edu.poly.shop.service.impl;

import edu.poly.shop.repository.IOrderDetailRepository;
import edu.poly.shop.service.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Override
    public List<Object[]> banChay(){
        return orderDetailRepository.banChay();
    }

    @Override
    public List<Object[]> doanhThu() {
        return orderDetailRepository.doanhThu();
    }
}
