package ru.simonov.hibernete.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import ru.simonov.hibernete.dao.UserRepository;
import ru.simonov.hibernete.entity.User;
import ru.simonov.hibernete.exeption.MyException;

import java.time.LocalDate;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User createUser(String name, String email, int age) {

        User user = User.builder()
                .nameUser(name)
                .email(email)
                .age(age)
                .created_at(LocalDate.now())
                .build();

        userRepository.save(user);
        return user;
    }

    @Override
    public void updateUser(int id, String name, String email, int age) {
        userRepository.findById(id)
                .ifPresentOrElse(user1 -> {
                    user1.setNameUser(name);
                    user1.setEmail(email);
                    user1.setAge(age);
                    userRepository.update(user1);
                }, () -> {
                    new MyException("пользователь с id = " + id + " не найден");
                });
    }

    @Override
    public void deleteUser(int id){
       User user = findUser(id);
       userRepository.delete(user);
    }

    @Override
    public User findUser(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new MyException("Пользователь не найден: id=" + id));
    }

}
