package edu.poly.shop.service.impl;

import edu.poly.shop.beans.__Login;
import edu.poly.shop.beans.__UserModel;
import edu.poly.shop.entities._User;
import edu.poly.shop.repository.IUserRepository;
import edu.poly.shop.service.IUserService;
import edu.poly.shop.utils.EmailUtil;
import edu.poly.shop.utils.EncryptUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.criteria.CriteriaBuilder;
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
    public void login(String email, String password) {
        Optional<_User> user = findByEmail(email);

        if (user.isPresent()) {
            boolean checkPass = EncryptUtil.check(password, user.get().getPassword());
            if (checkPass) {
                session.setAttribute("username", new __Login(email, password, user.get().getIsAdmin()));
            }
        }
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
            //update
            id = optional_pardigm.get().getId();
            crDate = optional_pardigm.get().getCreateDate();
            if (dto.getPassword().equals("")) {
                dto.setPassword(optional_pardigm.get().getPassword());
            } else {
                if (dto.getPassword() == dto.getConfirm_password()) {
                    dto.setPassword(EncryptUtil.encrypt(dto.getPassword()));
                }
            }
//                redirectAttributes.addFlashAttribute("messageTC","Cập nhập tài khoản thành công");
        } else {
//            add
                dto.setPassword(EncryptUtil.encrypt(dto.getPassword()));
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

    @Override
    public void dkm(String pass1, String pass2, String pass3, RedirectAttributes redirectAttributes) {
        System.out.println(pass1 + pass2 + pass3);
        __Login login = (__Login) session.getAttribute("username");
        Optional<_User> user = findByEmail(login.getEmail());
        boolean check = EncryptUtil.check(pass1, user.get().getPassword());
        if (!check) {
            redirectAttributes.addFlashAttribute("errors", "Mật khẩu cũ không đúng");
        } else {
            if (!pass2.equals(pass3)) {
                redirectAttributes.addFlashAttribute("errors", "Xác nhận mật khẩu mới không khớp");
            } else {
                user.get().setPassword(EncryptUtil.encrypt(pass2));
                userRepository.save(user.get());
                redirectAttributes.addFlashAttribute("messageTC", "Đổi mật khẩu thành công");
            }
        }
    }

    @Override
    public void qmk(String email, RedirectAttributes redirectAttributes) {
        Optional<_User> user = findByEmail(email);
        String NewPass = EmailUtil.sendMail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errors", "Email không tồn tại hoặc chưa đăng ký tài khoản");
        } else {
            user.get().setPassword(EncryptUtil.encrypt(NewPass));
            userRepository.save(user.get());
            redirectAttributes.addFlashAttribute("messageTC", "Mật khẩu mới đã được gửi tới email");
        }
    }


//    public void qmk(String email, String maForm, String pass1, String pass2, RedirectAttributes redirectAttributes){
//        Optional<_User> user = findByEmail(email);
//       String ma = EmailUtil.sendMail(email);
//       if (user == null){
//           redirectAttributes.addFlashAttribute("msg","Email không tồn tại hoặc chưa đăng ký tài khoản");
//       }else {
//        if (!ma.equals(maForm)){
//            redirectAttributes.addFlashAttribute("msg","Mã xác nhận không chính xác");
//        }else {
//            if (!pass1.equals(pass2)){
//                redirectAttributes.addFlashAttribute("msg","Xác nhận mật khảu không chính xác");
//            }else {
//                user.get().setPassword(EncryptUtil.encrypt(pass1));
//                userRepository.save(user.get());
//                redirectAttributes.addFlashAttribute("msgTC","Cập nhập mật khẩu mới thành công");
//            }
//        }
//       }
//    }
}
