package pl.edu.agh.hibernate.example.shopapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.cfg.Configuration;
import pl.edu.agh.hibernate.example.shopapp.controller.ShopAppController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main extends Application {
    private static EntityManagerFactory entityManagerFactory = null;

    private Stage primaryStage;
    private ShopAppController shopAppController;

    public static void main(String[] args) {
        launch(args);
    }

    private static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            Configuration configuration = new Configuration();
            entityManagerFactory = configuration.configure().buildSessionFactory();
        }
        return entityManagerFactory;
    }

    private static ShopService startDatabaseRoutine() {
        EntityManagerFactory emf = getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        ShopDatabaseFiller.fillDatabase(em);
        return new ShopService(em);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MakeOrder App");

        ShopService shopService = startDatabaseRoutine();
        this.shopAppController = new ShopAppController(primaryStage);
        shopAppController.initRootLayout(shopService);
    }

    @Override
    public void stop() {
        entityManagerFactory.close();
    }
}