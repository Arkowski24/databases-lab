package pl.edu.agh.hibernate.example.model.order;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pl.edu.agh.hibernate.example.model.product.Product;

import javax.persistence.*;

@Entity
@Access(AccessType.PROPERTY)
public class OrderItem {
    private int id;
    private Product product;
    private IntegerProperty orderedUnits;

    public OrderItem() {
    }

    public OrderItem(int id, Product product, int orderedUnits) {
        this.id = id;
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
    @JoinColumn
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

    public IntegerProperty rderedUnitsProperty() {
        return orderedUnits;
    }
}
