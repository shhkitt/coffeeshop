package ru.shhkitt.coffeeshop.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shhkitt.coffeeshop.entities.CartItem;
import ru.shhkitt.coffeeshop.entities.Item;

import java.util.List;


public interface ItemsService {
public void add(Item item);
public void delete(int id);
public List<Item> getAll();
public Item getById(int id);
public List<Item> filterByType(String type);
public void addCartItem(Item item, CartItem basket_item);
}
