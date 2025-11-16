package ru.simonov.hibernete.service;

import ru.simonov.hibernete.entity.User;

public interface UserService {
    User createUser(String name, String email, int age);
    void updateUser(int id, String name, String email, int age);
    void deleteUser(int id);
    User findUser(int id);
}
