package ru.shhkitt.coffeeshop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shhkitt.coffeeshop.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartById(int id);
}
