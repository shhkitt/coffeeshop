package ru.shhkitt.coffeeshop.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shhkitt.coffeeshop.entities.Item;

import java.util.Optional;

@Repository
public interface ItemsRepo extends JpaRepository<Item, Integer> {
    Optional<Item> findByName(String name);
    Optional<Item> findAllByType(String type);
}
