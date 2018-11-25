package pl.edu.agh.hibernate.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    private static SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Product product = readProduct();
        session.save(product);

        tx.commit();
        session.close();
        sessionFactory.close();
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory = configuration.configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    private static void saveSupplier(Session session) {
        Supplier supplier = readSupplier();
        session.save(supplier);
    }

    private static Product findProduct(Session session, String productName){
        return session.find(Product.class, productName);
    }

    private static Product readProduct() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Product name:");
        String prodName = inputScanner.nextLine();

        System.out.println("Units count:");
        int unitsCount = inputScanner.nextInt();

        return new Product(prodName, unitsCount, null);
    }

    private static Supplier readSupplier() {
        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Company name:");
        String companyName = inputScanner.nextLine();

        System.out.println("Street name:");
        String streetName = inputScanner.nextLine();

        System.out.println("City name:");
        String cityName = inputScanner.nextLine();

        return new Supplier(companyName, streetName, cityName);
    }
}
