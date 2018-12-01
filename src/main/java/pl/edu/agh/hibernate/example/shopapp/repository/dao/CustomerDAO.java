package pl.edu.agh.hibernate.example.shopapp.repository.dao;

import pl.edu.agh.hibernate.example.shopapp.model.company.Customer;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerDAO {
    private EntityManager em;

    public CustomerDAO(EntityManager em) {
        this.em = em;
    }

    public List<Customer> getAllCustomers() {
        String queryString = "select c from Customer c ";
        TypedQuery<Customer> query = em.createQuery(queryString, Customer.class);

        return query.getResultList();
    }

    public Customer findCustomerByName(String companyName) {
        String queryString = "select c from Customer c where c.companyName = :name";

        TypedQuery<Customer> query = em.createQuery(queryString, Customer.class);
        query.setParameter("name", companyName);

        return query.getSingleResult();
    }
}
