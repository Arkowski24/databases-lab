package pl.edu.agh.hibernate.example.model;

import pl.edu.agh.hibernate.example.dao.ProductDao;
import pl.edu.agh.hibernate.example.model.product.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class Shop {
    private ProductDao productDao;

    public Shop(EntityManager em) {
        this.productDao = new ProductDao(em);
    }

    public List<Product> getAvailableProducts() {
        return productDao.getAllAvailableProducts();
    }
}
