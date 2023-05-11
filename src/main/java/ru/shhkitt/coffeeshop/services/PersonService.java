package ru.shhkitt.coffeeshop.services;

import org.springframework.stereotype.Service;
import ru.shhkitt.coffeeshop.entities.Cart;
import ru.shhkitt.coffeeshop.entities.Person;

import java.util.Optional;

public interface PersonService {
    public Optional<Person> findByUsername(String username);
    public Person currentPerson();
    public void addCart(Person person, Cart cart);
}
