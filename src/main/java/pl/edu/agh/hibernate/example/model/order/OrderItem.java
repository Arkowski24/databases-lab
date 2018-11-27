package pl.edu.agh.hibernate.example.model.order;

import javafx.beans.property.IntegerProperty;
import pl.edu.agh.hibernate.example.model.product.Product;

import javax.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn
    private Product product;

    private IntegerProperty orderedUnits;

    public OrderItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderedUnits() {
        return orderedUnits.get();
    }

    public void setOrderedUnits(int orderedUnits) {
        this.orderedUnits.set(orderedUnits);
    }

    public IntegerProperty getOrderedUnitsProperty() {
        return orderedUnits;
    }

}
