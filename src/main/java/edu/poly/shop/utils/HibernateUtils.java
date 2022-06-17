package edu.poly.shop.utils;

import edu.poly.shop.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtils {
    private static final SessionFactory FACTORY;

    static {
        Configuration conf = new Configuration();



        Properties properties = new Properties();
        properties.put(Environment.DIALECT,"org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL,"jdbc:mysql://localhost:3306/asm_java5");
        properties.put(Environment.USER,"root");
        properties.put(Environment.PASS,"222222");
        properties.put(Environment.SHOW_SQL,"true");

        conf.setProperties(properties);
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
//        conf.addAnnotatedClass(_Paradigm.class);
//        conf.addAnnotatedClass(_Category.class);
//        conf.addAnnotatedClass(_Figure.class);
//        conf.addAnnotatedClass(_Material.class);
//        conf.addAnnotatedClass(_User.class);
//        conf.addAnnotatedClass(_Order.class);
//        conf.addAnnotatedClass(_Customer.class);
//        conf.addAnnotatedClass(_Orderdetail.class);
//        conf.addAnnotatedClass(_OrderDetaild.class);


        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory(registry);


//        conf.configure();
//        FACTORY = conf.buildSessionFactory();
    }

    public static SessionFactory getFactory(){
        return FACTORY;
    }
}
