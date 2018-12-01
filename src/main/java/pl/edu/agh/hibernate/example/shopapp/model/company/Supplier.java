package pl.edu.agh.hibernate.example.shopapp.model.company;

import pl.edu.agh.hibernate.example.shopapp.model.product.Product;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SUPPLIERS")
@Access(AccessType.PROPERTY)
public class Supplier extends Company {
    private List<Product> supplies;
    private String bankAccountNumber;

    public Supplier() {
    }

    public Supplier(String companyName, Address address, String bankAccountNumber) {
        super(companyName, address);
        this.bankAccountNumber = bankAccountNumber;
    }

    @OneToMany(mappedBy = "suppliedBy")
    public List<Product> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Product> supplies) {
        this.supplies = supplies;
    }


    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
