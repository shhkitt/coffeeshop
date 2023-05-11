package ru.shhkitt.coffeeshop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cartitems")
@Getter
@Setter
public class CartItem {
    @Id
    @SequenceGenerator(name = "cartitems_seq", sequenceName =
            "cartitems_sequence", allocationSize = 1)
    @GeneratedValue(generator = "cartitems_seq", strategy =
            GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public CartItem() {
        this.quantity = 1;
    }

}