package ru.simonov.hibernete.console;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.simonov.hibernete.entity.User;

import java.time.LocalDate;
import java.util.Scanner;

@Data

@RequiredArgsConstructor
public class ReadConsole {
    private final Scanner scanner;

    public int selectAction() {
        System.out.println("Выбирете действие : ");
        System.out.println("""
                Добавить запись в таблицу - 1;
                Обновить таблицу  - 2;
                Удалить запись - 3;
                Читать данные таблицы - 4;   
                """);
        int num = Integer.parseInt(scanner.nextLine());
        if (!validActionNumber(num)) {
            System.out.println("введите заново, такого значения " + num + " нет");
            return selectAction();
        }
        return num;
    }

    public User newUser() {
        System.out.println("Введите данные пользователя:");

        System.out.println("Имя: ");
        String name = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        System.out.println("Возраст: ");
        int age = 0;
        while (true) {
            String ageInput = scanner.nextLine();
            try {
                age = Integer.parseInt(ageInput);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверный формат числа. Введите возраст снова: ");
            }
        }

        return User.builder()
                .nameUser(name)
                .email(email)
                .age(age)
                .created_at(LocalDate.now())
                .build();
    }

    public int getIdUser() {
        System.out.println("Введите id юзера ");
        int id = Integer.parseInt(scanner.nextLine());
        return id;
    }

    public User updateUser(User user) {
        System.out.println("Введите данные пользователя что бы поменять:");

        System.out.println("Имя: ");
        String name = scanner.nextLine();

        System.out.println("Email: ");
        String email = scanner.nextLine();

        System.out.println("Возраст: ");
        int age = 0;
        while (true) {
            String ageInput = scanner.nextLine();
            try {
                age = Integer.parseInt(ageInput);
                break;
            } catch (NumberFormatException e) {
                System.out.print("Неверный формат числа. Введите возраст снова: ");
            }
        }

        user.setNameUser(name);
        user.setEmail(email);
        user.setAge(age);
        return user;
    }

    private boolean validActionNumber(int actionNumber) {
        return actionNumber >= 1 && actionNumber <= 4;
    }
}
