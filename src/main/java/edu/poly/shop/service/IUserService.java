package edu.poly.shop.service;

import edu.poly.shop.beans.__Login;
import edu.poly.shop.beans.__UserModel;
import edu.poly.shop.entities._User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    __Login login(String email, String password);

    @Query("select u from _User u where u.status=1 and u.email=?1")
    Optional<_User> findByEmail(String email);

    List<_User> findAll();

    Page<_User> findAll(int pageNumber, int maxRecord);

    Optional<_User> findById(Integer integer);

    _User insert(__UserModel dto);

    _User delete(Integer id);
}
