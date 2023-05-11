package ru.shhkitt.coffeeshop.controllers;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shhkitt.coffeeshop.entities.Cart;
import ru.shhkitt.coffeeshop.entities.Item;
import ru.shhkitt.coffeeshop.entities.Person;
import ru.shhkitt.coffeeshop.repo.CartItemRepository;
import ru.shhkitt.coffeeshop.repo.CartRepository;
import ru.shhkitt.coffeeshop.services.CartService;
import ru.shhkitt.coffeeshop.services.CartItemService;
import ru.shhkitt.coffeeshop.services.ItemsService;
import ru.shhkitt.coffeeshop.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequestMapping("")
@Controller
public class CartController {
    private final PersonService personService;
    private final CartService cartService;
    private final CartItemService cart_itemService;
    private final ItemsService itemService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartController(PersonService personService, CartService cartService, CartItemService cartItemService, ItemsService itemService, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.personService = personService;
        cart_itemService = cartItemService;
        this.cartService = cartService;
        this.itemService = itemService;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping("cart/add/{id}")
    public String addedToCart(@PathVariable String id) {
        return "redirect:/menu";
    }

    @PostMapping("cart/add/{id}")
    public String addToCart(@PathVariable(name = "id") int itemId) {
        Person currentPerson = personService.currentPerson();
        Item item = itemService.getById(itemId);
        if (currentPerson != null) {
            List<Cart> carts = currentPerson.getCarts();
            if (carts.isEmpty() || carts.get(carts.size()-1).is_completed()) {
                cartService.newCart(currentPerson);
                cart_itemService.addCartItem(carts.get(carts.size()-1), item);
            }
            else if (cartService.isCurrentCart(carts) != null) {
                cart_itemService.addCartItem(cartService.isCurrentCart(carts), item);
            }
            return "redirect:/menu";
        }

        return "redirect:/auth/login";
    }

    @GetMapping("cart")
    public String showCart(Model model){
        Person currentPerson = personService.currentPerson();
        if (currentPerson != null) {
            List<Cart> carts = currentPerson.getCarts();
            if (carts.isEmpty() || carts.get(carts.size()-1).is_completed()) {
                cartService.newCart(currentPerson);
            }
            model.addAttribute("cart", carts.get(carts.size()-1));
            return "cart/cart";
        }
        return "auth/login";

    }

    @PostMapping("order/add/{id}")
    public String order(@PathVariable int id){
        Cart order = cartService.getById(id);
        cartService.makeOrder(order);
        return "redirect:/order{id}";
    }

    @GetMapping("order{id}")
    public String getOrder(@PathVariable int id, Model model){
        Cart order = cartService.getById(id);
        model.addAttribute("order", order);
        return "cart/order";
    }
/*
    @GetMapping("cart/remove/{id}")
    public String removedFromCart(@PathVariable int id) {
        return "redirect:/cart";
    }

    @PostMapping("cart/remove/{id}")
    public String removeFromCart(@PathVariable(name = "id") int itemId) {
        Person currentPerson = personService.currentPerson();
        List<Cart> carts = currentPerson.getCarts();
        cart_itemService.removeCartItem(cartService.isCurrentCart(carts), itemId);
        return "redirect:/cart";
    }

 */
}
