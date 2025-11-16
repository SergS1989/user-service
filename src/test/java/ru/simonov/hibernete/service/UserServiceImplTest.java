package ru.simonov.hibernete.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.simonov.hibernete.dao.UserRepository;
import ru.simonov.hibernete.entity.User;
import ru.simonov.hibernete.exeption.MyException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = User.builder()
                .id(1)
                .nameUser("Serg")
                .email("Serg@mail.ru")
                .age(36)
                .created_at(LocalDate.now())
                .build();
    }

    @Test
    void givenUser_whenCreate_thenSaveInDb() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User userTestService = userService.createUser("Serg", "serg@mail.ru", 21);

        verify(userRepository).save(any(User.class));
        assertThat("Serg").isEqualTo(userTestService.getNameUser());
        assertThat("serg@mail.ru").isEqualTo(userTestService.getEmail());
        assertThat(21).isEqualTo(userTestService.getAge());

    }

    @Test
    void givenUser_whenUpdate_thenUpdateInDb() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).update(any(User.class));

        userService.updateUser(testUser.getId(), "Joun", "joun@mail.ru", 21);

        verify(userRepository).update(any(User.class));
        assertThat("Joun").isEqualTo(testUser.getNameUser());
        assertThat("joun@mail.ru").isEqualTo(testUser.getEmail());
        assertThat(21).isEqualTo(testUser.getAge());
    }

    @Test
    void givenUser_whenUpdate_thenUpdateInDb_NotFound() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUser(testUser.getId()))
                .isExactlyInstanceOf(MyException.class)
                .hasMessage("Пользователь не найден: id=" + testUser.getId());
    }

    @Test
    void givenUser_whenFind_thenFindInDb() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        User foundUser = userService.findUser(testUser.getId());

        assertThat(testUser).isEqualTo(foundUser);
    }

    @Test
    void givenUser_whenFind_thenFindInDb_NotFound() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUser(testUser.getId()))
                .isExactlyInstanceOf(MyException.class)
                .hasMessage("Пользователь не найден: id=" + testUser.getId());
    }

    @Test
    void givenUser_whenDelete_thenDeleteInDb() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).delete(testUser);

        userService.deleteUser(testUser.getId());

        verify(userRepository).delete(testUser);
    }

    @Test
    void givenUser_whenDelete_thenDeleteInDb_NotFound() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUser(testUser.getId()))
                .isExactlyInstanceOf(MyException.class)
                .hasMessage("Пользователь не найден: id=" + testUser.getId());
    }
}