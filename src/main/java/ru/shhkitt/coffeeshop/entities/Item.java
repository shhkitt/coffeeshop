package ru.shhkitt.coffeeshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @SequenceGenerator(name = "items_seq", sequenceName =
            "items_sequence", allocationSize = 1)
    @GeneratedValue(generator = "items_seq", strategy =
            GenerationType.SEQUENCE)
    Long id;
    @Column(name = "type")
    String type;
    @Column(name = "name")
    String name;
    @Column(name = "price")
    int price;
    @Column(name = "description")
    String description;
    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItems;

    public String getStringPrice(){
        return price + "$";
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                "$ }";
    }
}
