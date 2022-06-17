package edu.poly.shop.service;

import edu.poly.shop.beans.__Login;
import edu.poly.shop.beans.__UserModel;
import edu.poly.shop.entities._User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    void login(String email, String password);

    @Query("select u from _User u where u.status=1 and u.email=?1")
    Optional<_User> findByEmail(String email);

    List<_User> findAll();

    Page<_User> findAll(int pageNumber, int maxRecord);

    Optional<_User> findById(Integer integer);

    _User insert(__UserModel dto);

    _User delete(Integer id);

    void dkm(String pass1, String pass2, String pass3, RedirectAttributes redirectAttributes);


    void qmk(String email, RedirectAttributes redirectAttributes);
}
