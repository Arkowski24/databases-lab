package pl.edu.agh.hibernate.example.shopapp.repository;

import pl.edu.agh.hibernate.example.shopapp.model.company.Customer;
import pl.edu.agh.hibernate.example.shopapp.model.order.Order;
import pl.edu.agh.hibernate.example.shopapp.model.product.Product;
import pl.edu.agh.hibernate.example.shopapp.repository.dao.CustomerDAO;
import pl.edu.agh.hibernate.example.shopapp.repository.dao.OrderDAO;
import pl.edu.agh.hibernate.example.shopapp.repository.dao.ProductDAO;

import javax.persistence.EntityManager;
import java.util.List;

public class ShopRepository {
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public ShopRepository(EntityManager em) {
        this.customerDAO = new CustomerDAO(em);
        this.orderDAO = new OrderDAO(em);
        this.productDAO = new ProductDAO(em);
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
        return productDAO.getAllAvailableProducts();
    }

    public void saveProducts(List<Product> products) {
        productDAO.saveProducts(products);
    }
}
