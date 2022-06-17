package edu.poly.shop.controller.user;

import edu.poly.shop.service.IUserService;
import edu.poly.shop.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/email")
public class MailController {
    @Autowired
    IUserService userService;


    @PostMapping("/send")
    public String sendMail(
            @RequestParam(value = "email",required = false) String email,
            RedirectAttributes redirectAttributes
    ) {
        userService.qmk(email,redirectAttributes);
        return "redirect:/shop/paradigm/";
    }
}
