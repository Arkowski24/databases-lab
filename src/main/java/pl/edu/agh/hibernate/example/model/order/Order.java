package pl.edu.agh.hibernate.example.model.order;

import pl.edu.agh.hibernate.example.model.company.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@Access(AccessType.PROPERTY)
public class Order {
    private int id;
    private Customer customer;
    private List<OrderItem> orderItems;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public Order() {
    }

    public Order(Customer customer) {
        this.customer = customer;
        this.orderItems = new ArrayList<>();
        this.status = OrderStatus.NEW;
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
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    @Column(name = "ORDER_DATE")
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
