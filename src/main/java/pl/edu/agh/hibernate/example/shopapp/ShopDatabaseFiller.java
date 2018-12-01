package pl.edu.agh.hibernate.example.shopapp;

import pl.edu.agh.hibernate.example.shopapp.model.company.Address;
import pl.edu.agh.hibernate.example.shopapp.model.company.Customer;
import pl.edu.agh.hibernate.example.shopapp.model.company.Supplier;
import pl.edu.agh.hibernate.example.shopapp.model.product.Category;
import pl.edu.agh.hibernate.example.shopapp.model.product.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class ShopDatabaseFiller {
    public static void fillDatabase(EntityManager em) {
        List<Supplier> suppliers = createSuppliers();
        List<Customer> customers = createCustomers();
        List<Category> categories = createCategories();
        List<Product> products = createProducts(suppliers, categories);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        saveSuppliers(em, suppliers);
        saveCustomers(em, customers);
        saveCategories(em, categories);
        saveProducts(em, products);
        transaction.commit();
    }

    private static List<Supplier> createSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(new Supplier("Joseph Toys", new Address("Hard River Str.", "Zywiec", "34-300"), "0000-0000-0000-0000"));
        suppliers.add(new Supplier("Pawel Fruits", new Address("Red Str.", "Bielsko-Biala", "33-300"), "0000-0000-0000-0000"));
        return suppliers;
    }

    private static List<Customer> createCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Joseph Black", new Address("Soft River Str.", "Zywiec", "34-300"), 0.05));
        customers.add(new Customer("Pawel Pablo", new Address("Blue Str.", "Bielsko-Biala", "33-300"), 0.1));
        return customers;
    }

    private static List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Toy"));
        categories.add(new Category("Fruit"));
        categories.add(new Category("Vegetable"));
        return categories;
    }

    private static List<Product> createProducts(List<Supplier> suppliers, List<Category> categories) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Teddy Bear", categories.get(0), 10, suppliers.get(0), 10.0));
        products.add(new Product("Doll", categories.get(0), 1, suppliers.get(0), 24.0));
        products.add(new Product("Apple", categories.get(1), 100, suppliers.get(1), 2.3));
        products.add(new Product("Peach", categories.get(1), 23, suppliers.get(1), 3.68));
        products.add(new Product("Carrot", categories.get(2), 3, suppliers.get(1), 0.89));
        return products;
    }

    private static void saveSuppliers(EntityManager em, List<Supplier> suppliers) {
        for (Supplier supplier : suppliers) {
            em.persist(supplier);
        }
    }

    private static void saveCustomers(EntityManager em, List<Customer> customers) {
        for (Customer customer : customers) {
            em.persist(customer);
        }
    }

    private static void saveCategories(EntityManager em, List<Category> categories) {
        for (Category category : categories) {
            em.persist(category);
        }
    }

    private static void saveProducts(EntityManager em, List<Product> products) {
        for (Product product : products) {
            em.persist(product);
        }
    }
}
