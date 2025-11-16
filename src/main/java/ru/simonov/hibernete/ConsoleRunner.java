package ru.simonov.hibernete;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.simonov.hibernete.console.ReadConsole;
import ru.simonov.hibernete.dao.UserRepository;

import ru.simonov.hibernete.entity.User;
import ru.simonov.hibernete.exeption.MyException;
import ru.simonov.hibernete.util.HibernetUtil;


import java.lang.reflect.Proxy;
import java.util.Scanner;

@Slf4j
public class ConsoleRunner {
    public static void main(String[] args) throws MyException {
        try (Scanner scanner = new Scanner(System.in);
             SessionFactory sessionFactory = HibernetUtil.bildSessionFactory();
                /*Session session = sessionFactory.getCurrentSession()*/) {
            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(),
                    new Class[]{Session.class},
                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

            ReadConsole readConsole = new ReadConsole(scanner);
            int num = readConsole.selectAction();

            session.beginTransaction();

            UserRepository userRepository = new UserRepository(User.class, session);
            switch (num) {
                case 1 -> {
                    User user = readConsole.newUser();
                    userRepository.save(user);
                }
                case 2 ->{
                    User user1 = userRepository.findById(readConsole.getIdUser()).get();
                    userRepository.update(readConsole.updateUser(user1));
                }
                case 3 ->{
                    userRepository.delete(readConsole.getIdUser());
                }
                case 4 -> System.out.println(userRepository.findById(readConsole.getIdUser()).get());
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            throw new MyException("Что то пошло не так!!!" + e);
        }
    }
}
