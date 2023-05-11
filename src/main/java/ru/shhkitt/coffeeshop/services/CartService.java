package ru.shhkitt.coffeeshop.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shhkitt.coffeeshop.entities.Cart;
import ru.shhkitt.coffeeshop.entities.CartItem;
import ru.shhkitt.coffeeshop.entities.Person;

import java.util.List;

public interface CartService {
    public List<Cart> findAll();
    public void save(Cart cart);
    public void newCart(Person person);
    public void addCartItem(Cart cart, CartItem cart_item);
    public Cart isCurrentCart(List<Cart> carts);
    public Cart getById(int id);
    public void makeOrder(Cart cart);
    public Cart dropCartItem(Cart cart, CartItem cart_item);

    public List<Cart> findOrders(List<Cart> carts);

}
