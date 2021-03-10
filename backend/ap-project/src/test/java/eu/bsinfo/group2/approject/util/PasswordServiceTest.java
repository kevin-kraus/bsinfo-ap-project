package eu.bsinfo.group2.approject.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PasswordServiceTest {

    /**
     * Should encode password correctly so it can be validated after.
     */
    @Test
    void verifyPassword() {
        PasswordService cut = new PasswordService();
        String encodedPassword = cut.encodePassword("Test");
        cut.verifyPassword("Test", encodedPassword);
    }
}
