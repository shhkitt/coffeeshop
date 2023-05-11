package ru.shhkitt.coffeeshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shhkitt.coffeeshop.entities.Cart;
import ru.shhkitt.coffeeshop.entities.Person;
import ru.shhkitt.coffeeshop.services.CartService;
import ru.shhkitt.coffeeshop.services.PeopleDetailsService;
import ru.shhkitt.coffeeshop.services.PersonService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    PersonService personService;
    @Autowired
    CartService cartService;

    @GetMapping()
    public String showProfile(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //если пользователь - гость
        if (authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("person", null);
            return "redirect:/auth/login";
        }
            //если пользователь - юзер или админ
        else {
            String currentUserName = authentication.getName();
            Optional<Person> person = personService.findByUsername(currentUserName);
            Person current = person.get();
            model.addAttribute("person", current);
            List<Cart> carts = current.getCarts();
            List<Cart> orders = cartService.findOrders(carts);
            model.addAttribute("orders", orders);

        }
        return "/profile/profile";
    }
}
