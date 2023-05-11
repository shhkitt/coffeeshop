package ru.shhkitt.coffeeshop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shhkitt.coffeeshop.entities.CartItem;
import ru.shhkitt.coffeeshop.repo.CartItemRepository;
import ru.shhkitt.coffeeshop.services.CartItemService;
import ru.shhkitt.coffeeshop.services.CartService;
import ru.shhkitt.coffeeshop.services.ItemsService;
import ru.shhkitt.coffeeshop.entities.Cart;
import ru.shhkitt.coffeeshop.entities.Item;


@Service
@Transactional(readOnly = true)
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ItemsService itemsService;
    private final CartService cartService;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, ItemsService itemsService, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.itemsService = itemsService;
        this.cartService = cartService;
    }
    @Override
    @Transactional
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
        cartItem.getCart().setTotal_price(cartItem.getCart().getTotal_price() + cartItem.getItem().getPrice());
        cartService.save(cartItem.getCart());
    }
@Override
    @Transactional
    public void addCartItem(Cart cart, Item item) {

        //если товар с таким id уже есть, то прибавляем количество
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getItem().getId() == item.getId()) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                save(cartItem);
                return;
            }
        }

        //иначе создаем новый
        CartItem cartItem = new CartItem();

        cartItem.setItem(item);
        cartItem.setCart(cart);
        save(cartItem);

        itemsService.addCartItem(item, cartItem);
        cartService.addCartItem(cart, cartItem);
    }

    @Override
    @Transactional
    public void removeCartItem(Cart cart, int cart_item_id){
        CartItem cartItem = cartItemRepository.getReferenceById(cart_item_id);
        if (cartItem.getQuantity() != 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            save(cartItem);
            return;
        }
        else {
            cartService.dropCartItem(cart, cartItem);
            save(cartItem);
        }
    }
}
