package pl.edu.agh.hibernate.example.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.hibernate.example.ShopService;
import pl.edu.agh.hibernate.example.model.company.Customer;
import pl.edu.agh.hibernate.example.model.order.Order;
import pl.edu.agh.hibernate.example.model.order.OrderItem;
import pl.edu.agh.hibernate.example.model.order.OrderStatus;
import pl.edu.agh.hibernate.example.model.product.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MakeOrderController {
    private ShopService shopService;
    private BigDecimal total;

    @FXML
    private ComboBox<String> customersComboBox;

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
    private TableColumn<Product, BigDecimal> priceColumn;

    @FXML
    private TableView<OrderItem> cartProductsTable;
    @FXML
    private TableColumn<OrderItem, String> cartProductsNameColumn;
    @FXML
    private TableColumn<OrderItem, Integer> cartUnitsInStockColumn;
    @FXML
    private TableColumn<OrderItem, Integer> cartUnitsColumn;
    @FXML
    private TableColumn<OrderItem, BigDecimal> cartSumColumn;

    @FXML
    private Button addItemButton;
    @FXML
    private Button removeItemButton;
    @FXML
    private Button makeOrderButton;

    @FXML
    private TextField totalTextField;

    public MakeOrderController() {
        this.shopService = null;
    }

    public void setService(ShopService shopService) {
        this.shopService = shopService;

        fillCustomers();
        reinitializeTables();
    }

    private void fillCustomers() {
        List<Customer> customers = shopService.getAllCustomers();
        for (Customer c : customers) {
            customersComboBox.getItems().add(c.getCompanyName());
        }
    }

    @FXML
    private void initialize() {
        availableProductsNameColumn.setCellValueFactory(product -> product.getValue().productNameProperty());
        categoryNameColumn.setCellValueFactory(product -> product.getValue().getCategory().nameProperty());
        supplierNameColumn.setCellValueFactory(product -> product.getValue().getSuppliedBy().companyNameProperty());
        unitsInStockColumn.setCellValueFactory(product -> product.getValue().unitsInStockProperty().asObject());
        priceColumn.setCellValueFactory(product -> product.getValue().priceProperty());

        cartProductsNameColumn.setCellValueFactory(item -> item.getValue().getProduct().productNameProperty());
        cartUnitsInStockColumn.setCellValueFactory(item -> item.getValue().getProduct().unitsInStockProperty().asObject());
        cartUnitsColumn.setCellValueFactory(item -> item.getValue().orderedUnitsProperty().asObject());
        cartSumColumn.setCellValueFactory(item -> new ReadOnlyObjectWrapper(item.getValue().getSubTotal()));

        addItemButton.setOnAction(event -> addItemToCart());
        removeItemButton.setOnAction(event -> removeItemFromCart());
        makeOrderButton.setOnAction(event -> makeOrder());
    }

    private void addItemToCart() {
        Product product = availableProductsTable.getSelectionModel().getSelectedItem();
        if (product == null) return;

        availableProductsTable.getItems().remove(product);
        OrderItem orderItem = new OrderItem(product, 1);
        cartProductsTable.getItems().add(orderItem);

        total = total.add(orderItem.getSubTotal());
        updateTotalField();
    }

    private void removeItemFromCart() {
        OrderItem orderItem = cartProductsTable.getSelectionModel().getSelectedItem();
        if (orderItem == null) return;

        cartProductsTable.getItems().remove(orderItem);
        Product product = orderItem.getProduct();
        availableProductsTable.getItems().add(product);

        total = total.subtract(orderItem.getSubTotal());
        updateTotalField();
    }

    private void makeOrder() {
        String customerName = customersComboBox.getSelectionModel().getSelectedItem();
        if (customerName == null) {
            return;
        }

        Customer customer = shopService.findCustomerByName(customerName);
        Order order = new Order(customer);
        order.setOrderItems(cartProductsTable.getItems());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        shopService.saveOrder(order);
        updateUnitsInStock(order);

        reinitializeTables();
    }

    private void updateUnitsInStock(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            int newUnitsInStock = item.getProduct().getUnitsInStock() - item.getOrderedUnits();
            item.getProduct().setUnitsInStock(newUnitsInStock);
        }
    }

    private void reinitializeTables() {
        availableProductsTable.getItems().clear();
        availableProductsTable.getItems().setAll(shopService.getAvailableProducts());

        cartProductsTable.getItems().clear();

        this.total = BigDecimal.ZERO;
        updateTotalField();
    }

    private void updateTotalField() {
        String totalText = total.toPlainString();
        totalTextField.setText(totalText);
    }
}
