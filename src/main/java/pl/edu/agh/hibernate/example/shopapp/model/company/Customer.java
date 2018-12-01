package pl.edu.agh.hibernate.example.shopapp.model.company;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "CUSTOMERS")
@Access(AccessType.PROPERTY)
public class Customer extends Company {
    private BigDecimal discount;

    public Customer() {
    }

    public Customer(String companyName, Address address, BigDecimal discount) {
        super(companyName, address);
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
