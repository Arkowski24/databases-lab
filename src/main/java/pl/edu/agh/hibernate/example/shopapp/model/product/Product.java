package pl.edu.agh.hibernate.example.shopapp.model.product;

import javafx.beans.property.*;
import pl.edu.agh.hibernate.example.shopapp.model.company.Supplier;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Access(AccessType.PROPERTY)
public class Product {
    private int id;
    private StringProperty productName;
    private Category category;
    private IntegerProperty unitsInStock;
    private ObjectProperty<BigDecimal> price;
    private Supplier suppliedBy;

    public Product() {
    }

    public Product(String productName, Category category, int unitsInStock, Supplier suppliedBy, Double price) {
        this.productName = new SimpleStringProperty(productName);
        this.category = category;
        this.unitsInStock = new SimpleIntegerProperty(unitsInStock);
        this.suppliedBy = suppliedBy;
        this.price = new SimpleObjectProperty<>(BigDecimal.valueOf(price));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "name")
    public String getProductName() {
        return productName.getValue();
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public StringProperty productNameProperty() {
        return productName;
    }


    public int getUnitsInStock() {
        return unitsInStock.getValue();
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock.setValue(unitsInStock);
    }

    public IntegerProperty unitsInStockProperty() {
        return unitsInStock;
    }


    @ManyToOne
    @JoinColumn(name = "companyName")
    public Supplier getSuppliedBy() {
        return suppliedBy;
    }

    public void setSuppliedBy(Supplier suppliedBy) {
        this.suppliedBy = suppliedBy;
    }


    @ManyToOne
    @JoinColumn(name = "categoryID")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public BigDecimal getPrice() {
        return price.get();
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }
}
