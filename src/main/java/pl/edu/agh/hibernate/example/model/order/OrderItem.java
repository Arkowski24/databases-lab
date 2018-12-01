package pl.edu.agh.hibernate.example.model.order;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pl.edu.agh.hibernate.example.model.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ORDERS_ITEMS")
@Access(AccessType.PROPERTY)
public class OrderItem {
    private int id;
    private Product product;
    private IntegerProperty orderedUnits;

    public OrderItem() {
    }

    public OrderItem(Product product, int orderedUnits) {
        this.product = product;
        this.orderedUnits = new SimpleIntegerProperty(orderedUnits);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ManyToOne
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
        return product.getPrice().multiply(BigDecimal.valueOf(orderedUnits.getValue()));
    }
}
