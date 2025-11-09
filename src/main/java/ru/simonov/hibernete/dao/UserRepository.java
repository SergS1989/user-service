package ru.simonov.hibernete.dao;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.simonov.hibernete.entity.User;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepository implements Repository<Integer, User>{

    private final Class<User> clazz;

    private final EntityManager entityManager;

    @Override
    public User save(User entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.flush();
    }

    @Override
    public void update(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }
}
