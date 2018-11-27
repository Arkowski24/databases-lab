package pl.edu.agh.hibernate.example.model.company;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Company {
    private StringProperty companyName;

    @Embedded
    private Address address;

    public Company() {
    }

    public Company(String companyName, Address address) {
        this.companyName = new SimpleStringProperty(companyName);
        this.address = address;
    }

    @Id
    public String getCompanyName() {
        return companyName.getValue();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
