package edu.poly.shop.utils;

import edu.poly.shop.beans.Email;
import edu.poly.shop.config.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Properties;

public class EmailUtil {

   static JavaMailSender sender = new MailSender().getJavaMailSender();

    public static String sendMail(String to){
        String ma = RandomUtil.Random.randomAlphaNumeric(5);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject("Quên mật khẩu");
        msg.setText(
                "Mã xác nhận: "+ ma
                );
        sender.send(msg);
        return ma;
    }
}
