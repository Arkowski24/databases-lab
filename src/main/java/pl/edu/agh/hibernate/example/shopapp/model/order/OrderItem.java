package pl.edu.agh.hibernate.example.shopapp.model.order;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.edu.agh.hibernate.example.shopapp.model.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDERS_ITEMS")
@Access(AccessType.PROPERTY)
public class OrderItem {
    private int id;
    private Product product;
    private IntegerProperty orderedUnits;
    private ObjectProperty<BigDecimal> subTotal;

    public OrderItem() {
    }

    public OrderItem(Product product, int orderedUnits) {
        this.product = product;
        this.orderedUnits = new SimpleIntegerProperty(orderedUnits);
        this.subTotal = new SimpleObjectProperty<>(BigDecimal.ZERO);
        recalculateSubTotal();

        this.orderedUnits.addListener((obj, old_val, new_val) -> recalculateSubTotal());
        this.product.priceProperty().addListener((obj, old_val, new_val) -> recalculateSubTotal());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ManyToOne(cascade = CascadeType.ALL)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    @Column(name = "ORDERED_UNITS")
    public int getOrderedUnits() {
        return orderedUnits.get();
    }

    public void setOrderedUnits(int orderedUnits) {
        this.orderedUnits.set(orderedUnits);
    }

    public IntegerProperty orderedUnitsProperty() {
        return orderedUnits;
    }


    @Transient
    public BigDecimal getSubTotal() {
        return subTotal.getValue();
    }

    public ObjectProperty<BigDecimal> subTotalProperty() {
        return subTotal;
    }

    private void recalculateSubTotal() {
        BigDecimal productPrice = getProduct().getPrice();
        BigDecimal productUnits = BigDecimal.valueOf(orderedUnits.getValue());
        BigDecimal newValue = productPrice.multiply(productUnits);

        subTotal.setValue(newValue);
    }
}
