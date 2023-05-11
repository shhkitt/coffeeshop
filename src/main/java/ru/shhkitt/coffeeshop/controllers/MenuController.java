package ru.shhkitt.coffeeshop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.shhkitt.coffeeshop.entities.Item;
import ru.shhkitt.coffeeshop.entities.Person;
import ru.shhkitt.coffeeshop.services.CartItemService;
import ru.shhkitt.coffeeshop.services.ItemsService;
import ru.shhkitt.coffeeshop.services.PersonService;

import java.util.Optional;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    ItemsService itemsService;
    @Autowired
    PersonService personService;
    @Autowired
    CartItemService cartitemService;



    @GetMapping()
    public String menu(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //если пользователь - гость
        if (authentication instanceof AnonymousAuthenticationToken)
            model.addAttribute("person", null);

            //если пользователь - юзер или админ
        else {
            String currentUserName = authentication.getName();
            Optional<Person> person = personService.findByUsername(currentUserName);
            Person current = person.get();
            model.addAttribute("person", current);
        }

        model.addAttribute("CartItemService", cartitemService);
        model.addAttribute("Items", itemsService.getAll());
        return "menu/all";
    }

    @GetMapping("/item{id}")
    public String showOne(Model model, @PathVariable int id){
        model.addAttribute("Item", itemsService.getById(id));
        return "menu/item";
    }
    @GetMapping("/add")
    public String getAdd(@ModelAttribute("item") Item item){
        return "menu/add";
    }
    @PostMapping("/add")
    public String postAdd(@ModelAttribute("item") Item item){
        itemsService.add(item);
        return "menu/add";
    }
}
