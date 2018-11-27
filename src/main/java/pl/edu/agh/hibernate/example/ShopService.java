package pl.edu.agh.hibernate.example;

import pl.edu.agh.hibernate.example.dao.ProductDAO;
import pl.edu.agh.hibernate.example.model.product.Product;

import javax.persistence.EntityManager;
import java.util.List;

public class ShopService {
    private ProductDAO productDao;

    public ShopService(EntityManager em) {
        this.productDao = new ProductDAO(em);
    }

    public List<Product> getAvailableProducts() {
        return productDao.getAllAvailableProducts();
    }
}
