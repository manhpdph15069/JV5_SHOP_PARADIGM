package edu.poly.shop.service.impl;

import edu.poly.shop.beans.__CustomerModel;
import edu.poly.shop.beans.__Login;
import edu.poly.shop.entities.*;
import edu.poly.shop.repository.*;
import edu.poly.shop.service.ICartService;
import edu.poly.shop.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    private static final String ATT_CART_NAME = "myCart";
    @Autowired
    private HttpServletRequest request;
    @Autowired
    HttpSession session;
    @Autowired
    IParadigmRepository paradigmRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    ICartService cartService;


    @Override
    public String add(__CustomerModel customerModel, RedirectAttributes redirectAttributes) {

        //lấy danh sách sản phẩm về
        Object obj = cartService.getAllItem();
        if (obj != null) {
            __Login login = (__Login) session.getAttribute("username");
            _Order order = new _Order();
            _Customer customer = null;
            List<_Cart> map = new ArrayList<>(cartService.getAllItem());
            if (login != null) {
                Optional<_User> user = userRepository.findByEmail(login.getEmail());
                order.setUser(user.get());
                System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
            } else {
                customer  = new _Customer();
                BeanUtils.copyProperties(customerModel, customer);
                customerRepository.save(customer);
                order.setCustomer(customer);
                System.out.println("ccccccccccccccccccccccccccccc");
            }

            //tạo để lấy id
            orderRepository.save(order);

            BigDecimal total = new BigDecimal(0.0);
            String message = "";
            //duyệt từng sp 1 vào hóa đơn chi tiết
            for (_Cart entry : map) {
                Optional<_Paradigm> paradigmCheckSL = paradigmRepository.findById(Integer.valueOf(entry.getProductId()));
                if (paradigmCheckSL.get().getQuantity() >= entry.getQty()) {
                    _Orderdetail orderDetails = new _Orderdetail();
                    orderDetails.setPurchasedQuantity(entry.getQty());
                    orderDetails.setParadigm(paradigmCheckSL.get());
                    orderDetails.setPurchasedMoney(entry.getPrice());
                    orderDetails.setOrder(order);
                    orderDetailRepository.save(orderDetails);

                    //cập nhập lại số lượng sản phẩm
                    paradigmCheckSL.get().setQuantity(paradigmCheckSL.get().getQuantity() - entry.getQty());
                    paradigmRepository.save(paradigmCheckSL.get());
                    total = total.add(new BigDecimal(orderDetails.getPurchasedQuantity()).multiply(orderDetails.getPurchasedMoney()));

                } else {
                    if (order != null || customer != null) {
                        orderRepository.deleteById(order.getId());
                        customerRepository.deleteById(customer.getId());
                        System.out.println(customer.getId()+"ooooooooooooooooooooooo");
                    }
                    redirectAttributes.addFlashAttribute("errors", "Sản phẩm bạn mua chỉ còn " + paradigmCheckSL.get().getQuantity() + " vui lòng không mua quá số lượng tồn");
                    message += entry.getNamePro() + ", ";
                    return "redirect:/shop/paradigm/cart";
                }
            }
            order.setTotal(total);
            orderRepository.save(order);
            cartService.clead();
            redirectAttributes.addFlashAttribute("messageTC", "Đặt đơn thành công vui lòng đợi xác nhận");
            return "redirect:/shop/paradigm/";
        } else {
//            redirectAttributes.addFlashAttribute("error", "Đặt đơn không thành công vui lòng chọn sản phẩm muốn mua");
            return "redirect:/shop/paradigm/";
        }
    }


    @Override
    public Page<_Order> findAllPage(int pageNumber, int maxRecord) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        Page<_Order> page = orderRepository.findAll(pageable);
        return page;

    }

    @Override
    public _Order done(Integer id) {
        Optional<_Order> order = findById(id);
        order.get().setStatus(3);
        return orderRepository.save(order.get());
    }

    @Override
    public _Order confirm(Integer id) {
        Optional<_Order> order = findById(id);
        order.get().setStatus(2);
        return orderRepository.save(order.get());
    }

    @Override
    public _Order cancel(Integer id) {
        Optional<_Order> order = findById(id);
        order.get().setStatus(4);
        return orderRepository.save(order.get());
    }

    @Override
    public List<_Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<_Order> findById(Integer integer) {
        return orderRepository.findById(integer);
    }
}
