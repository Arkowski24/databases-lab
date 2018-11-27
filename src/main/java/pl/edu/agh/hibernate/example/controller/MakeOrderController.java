package pl.edu.agh.hibernate.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.edu.agh.hibernate.example.ShopService;
import pl.edu.agh.hibernate.example.model.order.OrderItem;
import pl.edu.agh.hibernate.example.model.product.Product;

public class MakeOrderController {
    private ShopService shopService;

    @FXML
    private TableView<Product> availableProductsTable;
    @FXML
    private TableColumn<Product, String> availableProductsNameColumn;
    @FXML
    private TableColumn<Product, String> categoryNameColumn;
    @FXML
    private TableColumn<Product, String> supplierNameColumn;
    @FXML
    private TableColumn<Product, Integer> unitsInStockColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableView<OrderItem> orderedProductsTable;
    @FXML
    private TableColumn<OrderItem, String> orderedProductsNameColumn;
    @FXML
    private TableColumn<OrderItem, Integer> orderedUnitsInStockColumn;
    @FXML
    private TableColumn<OrderItem, Integer> orderedUnitsColumn;
    @FXML
    private TableColumn<OrderItem, Double> orderedSumColumn;

    public MakeOrderController() {
        this.shopService = null;
    }

    public void setService(ShopService shopService) {
        this.shopService = shopService;
        availableProductsTable.getItems().setAll(shopService.getAvailableProducts());
    }

    @FXML
    private void initialize() {
        availableProductsNameColumn.setCellValueFactory(product -> product.getValue().productNameProperty());
        categoryNameColumn.setCellValueFactory(product -> product.getValue().getCategory().nameProperty());
        supplierNameColumn.setCellValueFactory(product -> product.getValue().getSuppliedBy().companyNameProperty());
        unitsInStockColumn.setCellValueFactory(product -> product.getValue().unitsInStockProperty().asObject());
        priceColumn.setCellValueFactory(product -> product.getValue().priceProperty().asObject());
    }
}
