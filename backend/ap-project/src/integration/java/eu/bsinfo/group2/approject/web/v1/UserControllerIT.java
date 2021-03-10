package eu.bsinfo.group2.approject.web.v1;

import eu.bsinfo.group2.approject.ApProjectApplication;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UserAlreadyExistsException;
import eu.bsinfo.group2.approject.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@Tag("IntegrationTest")
@SpringBootTest(
        classes = {ApProjectApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class UserControllerIT {

    @SpyBean
    private UserController userController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;


    /**
     * End-to-End Test if user is correctly created.
     */
    @Test
    public void shouldCreateUser() throws JSONException, UserAlreadyExistsException {
        // Request Setup
        final JSONObject requestBody = new JSONObject();
        requestBody.put("username", "testuser");
        requestBody.put("password", "testpassword");
        requestBody.put("firstName", "Test");
        requestBody.put("lastName", "User");
        requestBody.put("emailAddress", "integrationtest@test.de");
        requestBody.put("userType", "ADMIN");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user",
                        HttpMethod.PUT,
                        requestEntity,
                        String.class);

        // Verify correct function in UserController is called.
        verify(userController).createUser(any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify user is correctly added to the Database.
        Optional<UserDbo> savedUser = userRepository.findByUsername("testuser");
        assertThat(savedUser.isPresent()).isTrue();
        assertThat(savedUser.get().getFirstName()).isEqualTo("Test");
    }
}
