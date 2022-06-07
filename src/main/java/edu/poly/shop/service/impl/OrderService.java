package edu.poly.shop.service.impl;

import edu.poly.shop.entities._Customer;
import edu.poly.shop.entities._Order;
import edu.poly.shop.entities._Orderdetail;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.repository.ICustomerRepository;
import edu.poly.shop.repository.IOrderDetailRepository;
import edu.poly.shop.repository.IOrderRepository;
import edu.poly.shop.repository.IParadigmRepository;
import edu.poly.shop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
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

    @Override
    public String add() {
        HttpSession session = request.getSession();
        //lấy danh sách sản phẩm về
        Object obj = session.getAttribute(ATT_CART_NAME);
        if (obj != null) {
            //ép thành map
            Map<String, _Orderdetail> map = (Map<String, _Orderdetail>) obj;
            _Order order = new _Order();
//            _Users user = (_Users) session.getAttribute("user");
            Optional<_Customer> customer =customerRepository.findById(1);
            order.setCustomer(customer.get());
            //tạo để lấy id
            orderRepository.save(order);

            Double total = 0.0;
            String message = "";
            //duyệt từng sp 1 vào hóa đơn chi tiết
            for (Map.Entry<String, _Orderdetail> entry : map.entrySet()) {
                Optional<_Paradigm> paradigmCheckSL = paradigmRepository.findById(Integer.valueOf(entry.getKey()));
                if (paradigmCheckSL.get().getQuantity() >= entry.getValue().getPurchasedQuantity()){
                _Orderdetail orderDetails = entry.getValue();
                orderDetails.setOrder(order);
                orderDetailRepository.save(orderDetails);

                paradigmCheckSL.get().setQuantity(paradigmCheckSL.get().getQuantity()-entry.getValue().getPurchasedQuantity());
                paradigmRepository.save(paradigmCheckSL.get());
                total += orderDetails.getPurchasedQuantity() * orderDetails.getPurchasedMoney();

                }else {
                    message +=entry.getValue().getParadigm().getParadigmName() +",";
                }
            }
            System.out.println(message);
            order.setTotal(total);
            orderRepository.save(order);
            session.removeAttribute(ATT_CART_NAME);
            session.setAttribute("ordersuccess", "Đặt đơn thành công vui lòng đợi xác nhận");
            return "redirect:/shop/paradigm/";
        } else {
            session.setAttribute("order-fail", "Đặt đơn không thành công vui lòng chọn sản phẩm muốn mua");
            return "redirect:/shop/paradigm/";
        }
    }
}
