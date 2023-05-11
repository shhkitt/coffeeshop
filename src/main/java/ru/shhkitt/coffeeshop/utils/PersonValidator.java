package ru.shhkitt.coffeeshop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.shhkitt.coffeeshop.entities.Person;
import ru.shhkitt.coffeeshop.services.PeopleDetailsService;

@Component
public class PersonValidator implements Validator {
    private final PeopleDetailsService peopleDetailsService;

    @Autowired
    public PersonValidator(PeopleDetailsService peopleDetailsService) {
        this.peopleDetailsService = peopleDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person)target;

        try {
            peopleDetailsService.loadUserByUsername(person.getUsername());
        } catch(UsernameNotFoundException ignored) {
            return;
        }

        errors.rejectValue("username", "", "There is already person with that username");
    }
}
