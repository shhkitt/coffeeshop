package ru.shhkitt.coffeeshop.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shhkitt.coffeeshop.entities.CartItem;
import ru.shhkitt.coffeeshop.repo.CartRepository;
import ru.shhkitt.coffeeshop.repo.ItemsRepo;
import ru.shhkitt.coffeeshop.services.ItemsService;
import ru.shhkitt.coffeeshop.entities.Item;
import java.util.List;


@Service
@Slf4j
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    ItemsRepo itemsRepo;

    @Autowired
    CartRepository cartRepository;

    @Override
    @Transactional
    public void add(Item item) {
        itemsRepo.save(item);
    }

    @Override
    @Transactional
    public void delete(int id) {
        itemsRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getAll() {
        return itemsRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Item getById(int id) {
        return itemsRepo.findById(id).get();
    }

    @Override
    public List<Item> filterByType(String type){
        return itemsRepo.findAllByType(type).stream().toList();
    }

    @Override
    @Transactional
    public void addCartItem(Item item, CartItem basket_item) {
        item.getCartItems().add(basket_item);
        itemsRepo.save(item);
    }
}
