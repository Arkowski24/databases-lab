package pl.edu.agh.hibernate.example.model.order;

import pl.edu.agh.hibernate.example.model.company.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class Order {
    private int id;
    private Customer customer;
    private List<OrderItem> orderItems;
    private LocalDateTime orderDate;
    private OrderStatus status;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @OneToMany
    @JoinTable
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }


    @Enumerated(EnumType.STRING)
    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
