package ru.simonov.hibernete.dao;

import java.util.Optional;

public interface Repository<K, E> {
    E save(E entity);
    void delete(K id);
    void update(E entity);
    Optional<E> findById(K id);
}
