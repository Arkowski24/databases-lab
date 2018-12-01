package pl.edu.agh.hibernate.example.shopapp.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.agh.hibernate.example.shopapp.Main;
import pl.edu.agh.hibernate.example.shopapp.model.order.OrderItem;
import pl.edu.agh.hibernate.example.shopapp.presenter.EditItemPresenter;
import pl.edu.agh.hibernate.example.shopapp.presenter.MakeOrderPresenter;
import pl.edu.agh.hibernate.example.shopapp.repository.ShopRepository;

import java.io.IOException;

public class ShopAppController {
    private Stage primaryStage;

    public ShopAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout(ShopRepository shopRepository) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/MakeOrder.fxml"));
            AnchorPane rootLayout = loader.load();

            MakeOrderPresenter controller = loader.getController();
            controller.setService(shopRepository);
            controller.setShopAppController(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showEditCartItemDialog(OrderItem orderItem) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/EditCartItem.fxml"));
            AnchorPane pane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("EditItem");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);


            EditItemPresenter presenter = loader.getController();
            presenter.setData(orderItem);
            presenter.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return presenter.isValid();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
