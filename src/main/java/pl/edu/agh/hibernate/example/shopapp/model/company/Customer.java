package pl.edu.agh.hibernate.example.shopapp.model.company;

import javax.persistence.Entity;

@Entity
public class Customer extends Company {
    private double discount;

    public Customer() {
    }

    public Customer(String companyName, Address address, double discount) {
        super(companyName, address);
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
