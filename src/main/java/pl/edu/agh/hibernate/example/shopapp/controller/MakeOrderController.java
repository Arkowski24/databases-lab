package pl.edu.agh.hibernate.example.shopapp.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.edu.agh.hibernate.example.shopapp.ShopService;
import pl.edu.agh.hibernate.example.shopapp.model.company.Customer;
import pl.edu.agh.hibernate.example.shopapp.model.order.Order;
import pl.edu.agh.hibernate.example.shopapp.model.order.OrderItem;
import pl.edu.agh.hibernate.example.shopapp.model.order.OrderStatus;
import pl.edu.agh.hibernate.example.shopapp.model.product.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MakeOrderController {
    private ShopAppController shopAppController;

    private ShopService shopService;
    private SimpleObjectProperty<BigDecimal> total;
    private ObservableList<OrderItem> orderItems;

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
    private TableColumn<OrderItem, BigDecimal> cartItemPriceColumn;
    @FXML
    private TableColumn<OrderItem, BigDecimal> cartSumColumn;

    @FXML
    private Button makeOrderButton;

    @FXML
    private TextField totalTextField;
    @FXML
    private TextField discountTextField;

    public MakeOrderController() {
        this.total = new SimpleObjectProperty<>(BigDecimal.ZERO);
        this.orderItems = FXCollections.observableArrayList();
        total.addListener((obj, oldVal, newVal) -> updateTotalField());
    }

    public void setService(ShopService shopService) {
        this.shopService = shopService;

        fillCustomers();
        reinitializeTables();
    }

    public void setShopAppController(ShopAppController shopAppController) {
        this.shopAppController = shopAppController;
    }

    private void fillCustomers() {
        List<Customer> customers = shopService.getAllCustomers();
        for (Customer c : customers) {
            customersComboBox.getItems().add(c.getCompanyName());
        }
    }

    private Customer getSelectedCustomer() {
        String customerName = customersComboBox.getSelectionModel().getSelectedItem();
        if (customerName == null) {
            return null;
        }
        return shopService.findCustomerByName(customerName);
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
        cartItemPriceColumn.setCellValueFactory(item -> item.getValue().getProduct().priceProperty());
        cartSumColumn.setCellValueFactory(item -> item.getValue().subTotalProperty());

        customersComboBox.getSelectionModel().selectedIndexProperty().addListener((obj, oldV, newV) -> updateMakeOrderButton());
        customersComboBox.getSelectionModel().selectedIndexProperty().addListener((obj, oldV, newV) -> updateDiscountField());
        cartProductsTable.itemsProperty().setValue(orderItems);
        orderItems.addListener((ListChangeListener.Change<? extends OrderItem> c) -> updateMakeOrderButton());
    }

    @FXML
    private void addItemToCart() {
        Product product = availableProductsTable.getSelectionModel().getSelectedItem();
        if (product == null) return;

        availableProductsTable.getItems().remove(product);
        OrderItem orderItem = new OrderItem(product, 1);
        cartProductsTable.getItems().add(orderItem);

        BigDecimal newTotal = total.getValue().add(orderItem.getSubTotal());
        total.set(newTotal);
    }

    @FXML
    private void removeItemFromCart() {
        OrderItem orderItem = cartProductsTable.getSelectionModel().getSelectedItem();
        if (orderItem == null) return;

        cartProductsTable.getItems().remove(orderItem);
        Product product = orderItem.getProduct();
        availableProductsTable.getItems().add(product);

        BigDecimal newTotal = total.getValue().subtract(orderItem.getSubTotal());
        total.set(newTotal);
    }

    @FXML
    private void editItemInCart() {
        OrderItem orderItem = cartProductsTable.getSelectionModel().getSelectedItem();
        if (orderItem == null) return;

        BigDecimal oldSubTotal = orderItem.getSubTotal();
        shopAppController.showEditCartItemDialog(orderItem);
        BigDecimal newSubTotal = orderItem.getSubTotal();

        BigDecimal newTotal = total.getValue().subtract(oldSubTotal).add(newSubTotal);
        total.set(newTotal);
    }

    @FXML
    private void makeOrder() {
        if (isNotValidOrder()) {
            return;
        }

        Customer customer = getSelectedCustomer();
        Order order = new Order(customer);
        order.setOrderItems(cartProductsTable.getItems());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW);

        shopService.saveOrder(order);
        updateUnitsInStock(order);

        reinitializeTables();
    }

    private boolean isNotValidOrder() {
        Customer customer = getSelectedCustomer();
        return (customer == null || cartProductsTable.getItems().size() == 0);
    }

    private void updateUnitsInStock(Order order) {
        order.getOrderItems().parallelStream()
                .forEach(item -> {
                    int newUnitsInStock = item.getProduct().getUnitsInStock() - item.getOrderedUnits();
                    item.getProduct().setUnitsInStock(newUnitsInStock);
                });
        List<Product> products = order.getOrderItems().parallelStream()
                .map(OrderItem::getProduct)
                .collect(Collectors.toList());
        shopService.saveProducts(products);
    }

    private void reinitializeTables() {
        availableProductsTable.getItems().clear();
        availableProductsTable.getItems().setAll(shopService.getAvailableProducts());

        orderItems.clear();
        this.total.set(BigDecimal.ZERO);
    }

    private void updateMakeOrderButton() {
        makeOrderButton.setDisable(isNotValidOrder());
    }

    private void updateTotalField() {
        BigDecimal totalValue = total.getValue();
        Customer customer = getSelectedCustomer();
        if (customer != null) {
            totalValue = totalValue.multiply(BigDecimal.ONE.subtract(customer.getDiscount()));
        }
        totalValue = totalValue.setScale(2, RoundingMode.HALF_UP);
        totalTextField.setText(totalValue.toPlainString());
    }

    private void updateDiscountField() {
        Customer customer = getSelectedCustomer();
        if (customer == null) {
            discountTextField.setText("0%");
        } else {
            BigDecimal discount = customer.getDiscount().multiply(BigDecimal.valueOf(100));
            discountTextField.setText(discount.toPlainString() + "%");
        }
        updateTotalField();
    }
}
