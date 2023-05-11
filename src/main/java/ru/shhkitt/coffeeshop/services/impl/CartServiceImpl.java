package ru.shhkitt.coffeeshop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shhkitt.coffeeshop.entities.Cart;
import ru.shhkitt.coffeeshop.entities.CartItem;
import ru.shhkitt.coffeeshop.entities.Person;
import ru.shhkitt.coffeeshop.repo.CartItemRepository;
import ru.shhkitt.coffeeshop.repo.CartRepository;
import ru.shhkitt.coffeeshop.services.CartService;
import ru.shhkitt.coffeeshop.services.PersonService;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final PersonService personService;

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, PersonService personService, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.personService = personService;
        this.cartItemRepository = cartItemRepository;
    }
    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }
    @Override
    @Transactional
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
    @Override
    @Transactional
    public void newCart(Person person) {
        Cart cart = new Cart();
        cart.setOwner(person);
        save(cart);
        personService.addCart(person, cart);
    }
    @Override
    @Transactional
    public void addCartItem(Cart cart, CartItem cart_item) {
        cart.getCartItems().add(cart_item);
        save(cart);
    }


    @Override
    @Transactional
    public Cart dropCartItem(Cart cart, CartItem cart_item) {
        /*
        if (cart_item.getQuantity() == 1) {
            cartItemRepository.delete(cart_item);
        }
        else{
            cart.getCartItems().
        }
        save(cart);

         */
        return cart;
    }



    @Override
    @Transactional(readOnly = true)
    public Cart getById(int id){
        return cartRepository.getReferenceById(id);
    }
@Override
@Transactional
    public void makeOrder(Cart cart){
        cart.setIs_completed(true);
    }
@Override
    public Cart isCurrentCart(List<Cart> carts) {
        for (Cart cart : carts) {
            if (!cart.is_completed())
                return cart;
        }
        return null;
    }

    @Override
    public List<Cart> findOrders(List<Cart> carts){
        return carts.stream().filter(Cart::is_completed).toList();
    }
}
