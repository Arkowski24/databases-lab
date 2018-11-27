package pl.edu.agh.hibernate.example.model.product;

import javafx.beans.property.*;
import pl.edu.agh.hibernate.example.model.company.Supplier;

import javax.persistence.*;

@Entity
@Table(name = "Products")
public class Product {
    private String id;
    private StringProperty productName;
    private IntegerProperty unitsInStock;
    private DoubleProperty price;

    @ManyToOne
    @JoinColumn(name = "companyName")
    private Supplier suppliedBy;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;


    public Product() {
    }

    public Product(String productName, int unitsInStock, Supplier suppliedBy, Double price) {
        this.productName = new SimpleStringProperty(productName);
        this.unitsInStock = new SimpleIntegerProperty(unitsInStock);
        this.suppliedBy = suppliedBy;
        this.price = new SimpleDoubleProperty(price);
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Basic
    public String getProductName() {
        return productName.getValue();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public StringProperty getProductNameProperty() {
        return productName;
    }


    @Basic
    public int getUnitsInStock() {
        return unitsInStock.getValue();
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock.setValue(unitsInStock);
    }

    public IntegerProperty getUnitsInStockProperty() {
        return unitsInStock;
    }

    public Supplier getSuppliedBy() {
        return suppliedBy;
    }

    public void setSuppliedBy(Supplier suppliedBy) {
        this.suppliedBy = suppliedBy;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Basic
    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty getPriceProperty() {
        return price;
    }
}
