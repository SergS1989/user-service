package ru.simonov.hibernete.service;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;

@RequiredArgsConstructor
public class UserConsoleController {
     private final Scanner scanner;
     private final UserService userService;

    public void run(){
        System.out.println("""
                    Выбирете действие:
                    1 - сохранить запись в базу
                    2 - изменить запись в базе
                    3 - удалить запись
                    4 - найти запись
                    """);
        int action = Integer.parseInt(scanner.nextLine());
        switch (action){
            case 1 -> createUser();
            case 2 -> updateUser();
            case 3 -> deleteUser();
            case 4 -> findUser();
            default -> System.out.println("некорректные данные");
        }
    }

    public void createUser(){
        System.out.println("Введите имя");
        String name = scanner.nextLine();

        System.out.println("Введите email");
        String email = scanner.nextLine();

        System.out.println("Введите возраст");
        int age = Integer.parseInt(scanner.nextLine());

        userService.createUser(name, email, age);
    }

    public void updateUser(){
        System.out.println("Введите id юзера :");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите имя");
        String name = scanner.nextLine();

        System.out.println("Введите email");
        String email = scanner.nextLine();

        System.out.println("Введите возраст");
        int age = Integer.parseInt(scanner.nextLine());

        userService.updateUser(id, name, email, age);
    }

    public void deleteUser(){
        System.out.println("Введите id юзера :");
        int id = Integer.parseInt(scanner.nextLine());

        userService.deleteUser(id);
    }

    public void findUser(){
        System.out.println("Введите id");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println(userService.findUser(id));
    }
}
