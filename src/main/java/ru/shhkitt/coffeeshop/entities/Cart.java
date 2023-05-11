package ru.shhkitt.coffeeshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter

public class Cart {
    @Id
    @SequenceGenerator(name = "carts_seq", sequenceName =
            "carts_sequence", allocationSize = 1)
    @GeneratedValue(generator = "carts_seq", strategy =
            GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "total_price")
    private int total_price;

    @Column(name = "is_completed")
    private boolean is_completed;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    public Cart() {
        this.total_price = 0;
        this.is_completed = false;
        this.cartItems = new ArrayList<>();
    }

    public void setIs_completed(boolean is_completed){
        this.is_completed = is_completed;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", owner=" + owner +
                ", total_price=" + total_price +
                ", is_completed=" + is_completed +
                ", cartItems=" + cartItems +
                '}';
    }
}
