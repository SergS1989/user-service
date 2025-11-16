package ru.simonov.hibernete.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.simonov.hibernete.entity.User;
import ru.simonov.hibernete.exeption.MyException;
import ru.simonov.hibernete.util.HibernetUtil;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Testcontainers
class UserRepositoryTest {

    private static SessionFactory sessionFactory;
    private static Session session;
    private static UserRepository userRepository;

    @BeforeEach
    void setUp() {
        sessionFactory = HibernetUtil.bildSessionFactory();

        session = sessionFactory.getCurrentSession();

        userRepository = new UserRepository(session);
    }

    @Test
    void givenUser_whenCreate_thenSaveAndFindInDb() {
        User user = User.builder()
                .nameUser("serg")
                .email("serg@89mail.ru")
                .age(21)
                .created_at(LocalDate.now())
                .build();

        log.info("создаем нового пользователя {}", user);

        session.beginTransaction();
        userRepository.save(user);
        Optional<User> foundUser = userRepository.findById(user.getId());
        log.info("ВЗЯЛИ ИЗ БАЗЫ {}", foundUser.get());
        session.getTransaction().commit();

        assertThat(user.getNameUser()).isEqualTo(foundUser.get().getNameUser());
    }

    @Test
    void givenUser_whenUpdate_thenUpdateInDb() {
        User user = User.builder()
                .nameUser("Alex")
                .email("Alex@89mail.ru")
                .age(25)
                .created_at(LocalDate.now())
                .build();

        session.beginTransaction();
        userRepository.save(user);
        user.setNameUser("Nikolai");
        userRepository.update(user);
        Optional<User> foundUser = userRepository.findById(user.getId());
        session.getTransaction().commit();

        assertThat(user.getNameUser()).isEqualTo(foundUser.get().getNameUser());
    }

    @Test
    void givenUser_whenDelete_thenDeleteInDb() {
        User user = User.builder()
                .nameUser("Ivan")
                .email("Ivan@89mail.ru")
                .age(25)
                .created_at(LocalDate.now())
                .build();

        session.beginTransaction();
        userRepository.save(user);
        userRepository.delete(user);
        Optional<User> foundUser = userRepository.findById(user.getId());
        session.getTransaction().commit();

        assertThat(foundUser.isPresent()).isFalse();
    }

    @Test
    void givenUser_whenUpdate_thenUpdateInDbNotFound() {
        User user = User.builder()
                .nameUser("Petr")
                .email("Petr@89mail.ru")
                .age(25)
                .created_at(LocalDate.now())
                .build();
        user.setId(999);

        session.beginTransaction();
        assertThatThrownBy(() -> userRepository.update(user))
                .isExactlyInstanceOf(MyException.class)
                .hasMessage("Такого пользователя нет");
        session.getTransaction().rollback();
    }

    @AfterEach
    void tearDown() {
        if (session != null && session.isOpen()) {
            session.close();
        }
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}