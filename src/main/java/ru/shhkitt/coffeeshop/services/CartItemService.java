package ru.shhkitt.coffeeshop.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shhkitt.coffeeshop.entities.Cart;
import ru.shhkitt.coffeeshop.entities.CartItem;
import ru.shhkitt.coffeeshop.entities.Item;

public interface CartItemService {
    public void save(CartItem cartItem);
    public void addCartItem(Cart cart, Item item);
    public void removeCartItem(Cart cart, int cart_item_id);
}
