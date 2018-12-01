package pl.edu.agh.hibernate.example.shopapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.cfg.Configuration;
import pl.edu.agh.hibernate.example.shopapp.controller.MakeOrderController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class Main extends Application {
    private static EntityManagerFactory entityManagerFactory = null;
    private Stage primaryStage;

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

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MakeOrder App");

        EntityManagerFactory emf = getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        ShopDatabaseFiller.fillDatabase(em);
        ShopService shopService = new ShopService(em);

        initRootLayout(shopService);
    }

    @Override
    public void stop() {
        entityManagerFactory.close();
    }

    private void initRootLayout(ShopService shopService) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/MakeOrder.fxml"));
            AnchorPane rootLayout = loader.load();

            MakeOrderController controller = loader.getController();
            controller.setService(shopService);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}