package pl.edu.agh.hibernate.example.shopapp.model.product;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORIES")
@Access(AccessType.PROPERTY)
public class Category {
    private int Id;
    private StringProperty name;
    private List<Product> products;

    public Category() {
    }

    public Category(String name) {
        this.name = new SimpleStringProperty(name);
        this.products = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return Id;
    }

    public void setId(int categoryID) {
        this.Id = categoryID;
    }


    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public StringProperty nameProperty() {
        return name;
    }


    @OneToMany(mappedBy = "category")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
