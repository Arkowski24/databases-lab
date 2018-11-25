package pl.edu.agh.hibernate.example;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private String productName;
    private int unitsInStock;
//    @OneToOne
//    private Supplier suppliedBy;

    public Product() {
    }

    public Product(String productName, int unitsInStock, Supplier suppliedBy) {
        this.productName = productName;
        this.unitsInStock = unitsInStock;
//        this.suppliedBy = suppliedBy;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

//    public Supplier getSuppliedBy() {
//        return suppliedBy;
//    }
//
//    public void setSuppliedBy(Supplier suppliedBy) {
//        this.suppliedBy = suppliedBy;
//    }
}
