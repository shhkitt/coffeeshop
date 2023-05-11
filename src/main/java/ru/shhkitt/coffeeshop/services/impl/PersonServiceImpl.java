package ru.shhkitt.coffeeshop.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shhkitt.coffeeshop.entities.Cart;
import ru.shhkitt.coffeeshop.entities.Person;
import ru.shhkitt.coffeeshop.repo.PeopleRepository;
import ru.shhkitt.coffeeshop.services.PersonService;

import java.util.Optional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {
    private final PeopleRepository personRepository;

    @Autowired
    public PersonServiceImpl(PeopleRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Override
    public Person currentPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //если пользователь - не гость
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return findByUsername(currentUserName).get();
        }

        //а если гость
        return null;
    }
    @Override
    @Transactional
    public void addCart(Person person, Cart cart) {
        person.getCarts().add(cart);
        personRepository.save(person);
    }
}
