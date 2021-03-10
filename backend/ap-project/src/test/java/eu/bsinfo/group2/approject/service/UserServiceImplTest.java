package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.entities.user.UserType;
import eu.bsinfo.group2.approject.exception.UserAlreadyExistsException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.repository.UserRepository;
import eu.bsinfo.group2.approject.util.PasswordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordService passwordService;

    private UserDbo newUser() {
        UserDbo newUser = new UserDbo();
        newUser.setUsername("testuser");
        newUser.setEmailAddress("test@test.de");
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setUserType(UserType.ADMIN);
        newUser.setPassword("testpassword");
        return newUser;
    }

    /**
     * Should attemt to create new User in the Database.
     */
    @Test()
    void createUser() throws UserAlreadyExistsException {
        UserServiceImpl cut = new UserServiceImpl(userRepository, passwordService);
        cut.createUser(newUser());
        verify(userRepository, times(1)).save(any());
    }

    /**
     * Should throw UserAlreadyExistsException if User with same Username already exists in the Database.
     */
    @Test()
    void createUserShouldThrow() {
        UserServiceImpl cut = new UserServiceImpl(userRepository, passwordService);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(newUser()));
        assertThrows(UserAlreadyExistsException.class, () -> cut.createUser(newUser()));
    }

    /**
     * Should attempt to save the updated User object.
     */
    @Test
    void updateUser() throws UserNotFoundException {
        UserServiceImpl cut = new UserServiceImpl(userRepository, passwordService);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(newUser()));
        UserDbo updatedUser = newUser();
        updatedUser.setId(1L);
        updatedUser.setFirstName("Test1");
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        UserDbo savedUser = cut.updateUser("testuser", updatedUser);
        verify(userRepository, times(1)).save(any());
        assertThat(savedUser.getFirstName()).isEqualTo("Test1");
    }

    /**
     * Should throw UserNotFoundException if user with username does not exist.
     */
    @Test
    void updateUserShouldThrow() {
        UserServiceImpl cut = new UserServiceImpl(userRepository, passwordService);
        UserDbo updatedUser = newUser();
        updatedUser.setFirstName("Test1");
        assertThrows(UserNotFoundException.class, () -> cut.updateUser("testuser", updatedUser));
    }

    /**
     * Should attempt to delete User from Database.
     */
    @Test
    void deleteUser() throws UserNotFoundException {
        UserServiceImpl cut = new UserServiceImpl(userRepository, passwordService);
        UserDbo userWithId = newUser();
        userWithId.setId(1L);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(userWithId));
        cut.deleteUser("testuser");
        verify(userRepository, times(1)).deleteById(1L);
    }

    /**
     * Should throw UserNotFoundException if user with username does not exist.
     */
    @Test
    void deleteUserShouldThrow() {
        UserServiceImpl cut = new UserServiceImpl(userRepository, passwordService);
        assertThrows(UserNotFoundException.class, () -> cut.deleteUser("testuser"));
    }
}
