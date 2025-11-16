package ru.simonov.hibernete.dao;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import ru.simonov.hibernete.entity.User;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager entityManager;

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }


    public void delete(User user) {
            entityManager.remove(user);
            entityManager.flush();
    }


    public void update(User user) {
        entityManager.merge(user);
    }


    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }
}
