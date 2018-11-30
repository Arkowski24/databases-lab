package pl.edu.agh.hibernate.example;

import pl.edu.agh.hibernate.example.dao.CustomerDAO;
import pl.edu.agh.hibernate.example.dao.OrderDAO;
import pl.edu.agh.hibernate.example.dao.ProductDAO;
import pl.edu.agh.hibernate.example.model.company.Customer;
import pl.edu.agh.hibernate.example.model.order.Order;
import pl.edu.agh.hibernate.example.model.product.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ShopService {
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private ProductDAO productDao;

    public ShopService(EntityManager em) {
        this.customerDAO = new CustomerDAO(em);
        this.orderDAO = new OrderDAO(em);
        this.productDao = new ProductDAO(em);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer findCustomerByName(String companyName) {
        return customerDAO.findCustomerByName(companyName);
    }

    public void saveOrder(Order order) {
        orderDAO.saveOrder(order);
    }

    public List<Product> getAvailableProducts() {
        return productDao.getAllAvailableProducts();
    }
}
