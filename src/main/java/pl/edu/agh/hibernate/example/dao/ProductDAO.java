package pl.edu.agh.hibernate.example.dao;

import pl.edu.agh.hibernate.example.model.product.Product;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductDAO {
    private EntityManager em;

    public ProductDAO(EntityManager em) {
        this.em = em;
    }

    public List<Product> getAllAvailableProducts() {
        String queryString = "select p from Product p where p.unitsInStock > 0";
        TypedQuery<Product> query = em.createQuery(queryString, Product.class);

        return query.getResultList();
    }
}
