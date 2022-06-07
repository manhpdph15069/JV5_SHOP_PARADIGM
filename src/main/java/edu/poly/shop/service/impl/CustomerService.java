package edu.poly.shop.service.impl;

import edu.poly.shop.repository.IUserRepository;
import edu.poly.shop.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private IUserRepository userRepository;
}
