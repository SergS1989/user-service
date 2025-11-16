package ru.simonov.hibernete;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.simonov.hibernete.dao.UserRepository;
import ru.simonov.hibernete.service.UserConsoleController;

import ru.simonov.hibernete.exeption.MyException;
import ru.simonov.hibernete.service.UserService;
import ru.simonov.hibernete.service.UserServiceImpl;
import ru.simonov.hibernete.util.HibernetUtil;


import java.lang.reflect.Proxy;
import java.util.*;

@Slf4j
public class ApplicationRunner {
    public static void main(String[] args) throws MyException {
        try (SessionFactory sessionFactory = HibernetUtil.bildSessionFactory();
             Scanner scanner = new Scanner(System.in);) {

            Session session = sessionFactory.getCurrentSession();

            UserRepository repo = new UserRepository(session);
            log.info("repo {}", repo);
            UserService userService = new UserServiceImpl(repo);
            UserConsoleController console = new UserConsoleController(scanner, userService);

            session.beginTransaction();
            console.run();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            throw new MyException("Что то пошло не так!!!" + e.getMessage());
        }
    }
}
