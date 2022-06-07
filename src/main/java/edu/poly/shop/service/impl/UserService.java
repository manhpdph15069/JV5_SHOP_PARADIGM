package edu.poly.shop.service.impl;

import edu.poly.shop.beans.__Login;
import edu.poly.shop.beans.__UserModel;
import edu.poly.shop.entities._User;
import edu.poly.shop.repository.IUserRepository;
import edu.poly.shop.service.IUserService;
import edu.poly.shop.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    HttpSession session;

    @Override
    public __Login login(String email, String password) {
        Optional<_User> user = findByEmail(email);
        System.out.println("ssssssssssssssssssssss");

        if (user.isPresent()){
            boolean checkPass = EncryptUtil.check(password, user.get().getPassword());
            if (checkPass){
        System.out.println("ssssssssssssssssssssss");
                session.setAttribute("username",new __Login(email,password));
                return new __Login(email,password);
            }
        }
    return null;
    }




//        if (user.isPresent()) {
//            boolean checkPass = EncryptUtil.check(password, user.get().getPassword());
//            if (checkPass) {
//                session.setAttribute("username", user.get().getFullname());
//                if (user.get().getIsAdmin()==0){
//                    return "redirect:/shop/paradigm/";
//                }else{
//                    return "redirect:/admin/paradigm/list";
//                }
//            }else {
//                session.setAttribute("message","Email hoặc mật khẩu không chính xác");
//                System.out.println("kd");
//                return "user/login";
//            }
//        }else {
//            return "redirect:/login";
//        }
//    }

    @Override
    @Query("select u from _User u where u.status=1 and u.email=?1")
    public Optional<_User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<_User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<_User> findAll(int pageNumber, int maxRecord) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        Page<_User> page = userRepository.findAll(pageable);
        return page;
    }

    @Override
    public Optional<_User> findById(Integer integer) {
        return userRepository.findById(integer);
    }

    @Override
    public _User insert(__UserModel dto) {
        Optional<_User> optional_pardigm = findByEmail(dto.getEmail());
        _User user = null;
        Integer id = null;
        Date crDate = null;
        if (optional_pardigm.isPresent()) {
            id = optional_pardigm.get().getId();
            crDate = optional_pardigm.get().getCreateDate();
            if (dto.getPassword().equals("")) {
                dto.setPassword(optional_pardigm.get().getPassword());
            } else {
                if (dto.getPassword() != dto.getConfirm_password()) {

                } else {
                    dto.setPassword(EncryptUtil.encrypt(dto.getPassword()));
                }
            }
            //update

        } else {
//            add
            _User userADD = new _User();
            userRepository.save(userADD);
            id = userADD.getId();
            crDate = new Date();
            dto.setPassword(EncryptUtil.encrypt(dto.getPassword()));
        }
        user = new _User(id,
                dto.getFullname(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getAddress(),
                crDate,
                1, 0);
        return userRepository.save(user);
    }


    @Override
    public _User delete(Integer id) {
        if (id != null) {
            Optional<_User> p = userRepository.findById(id);
            if (p.isPresent()) {
                userRepository.deleteById(id);
                return p.get();
            }
        }
        return null;
    }
}
