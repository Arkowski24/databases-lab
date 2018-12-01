package pl.edu.agh.hibernate.example.shopapp.repository.dao;

import pl.edu.agh.hibernate.example.shopapp.model.product.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

    public void saveProducts(List<Product> products) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        for (Product product : products) {
            em.persist(product);
        }
        et.commit();
    }
}
