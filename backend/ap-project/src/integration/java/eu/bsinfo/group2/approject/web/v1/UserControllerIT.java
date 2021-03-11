package eu.bsinfo.group2.approject.web.v1;

import eu.bsinfo.group2.approject.ApProjectApplication;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UserAlreadyExistsException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
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
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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


    /**
     * End-to-End Test if status code is 409 if user already exists.
     */
    @Test
    public void shouldReturn409OnExistingUser() throws JSONException, UserAlreadyExistsException {
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

        // Do throw exception.
        doThrow(new UserAlreadyExistsException()).when(userController).createUser(any());

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user",
                        HttpMethod.PUT,
                        requestEntity,
                        String.class);

        // Verify correct function in UserController is called.
        verify(userController).createUser(any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }


    /**
     * End-to-End Test if user is correctly updated in the Database.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldUpdateExistingUser() throws JSONException, UserNotFoundException {
        // Request Setup
        final JSONObject requestBody = new JSONObject();
        requestBody.put("username", "testuser");
        requestBody.put("password", "testpassword");
        requestBody.put("firstName", "UPDATE");
        requestBody.put("lastName", "User");
        requestBody.put("emailAddress", "integrationtest@test.de");
        requestBody.put("userType", "ADMIN");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        // Save user object, so that user exists
        UserDbo existingUser = new UserDbo();
        existingUser.setId(1L);
        existingUser.setUsername("testuser");
        existingUser.setFirstName("Test");
        userRepository.save(existingUser);


        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/testuser",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        // Verify correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify correct function in UserController is called.
        verify(userController).updateUser(eq("testuser"), any());

        // Verify changes were successfully saved to the database.
        Optional<UserDbo> savedUser = userRepository.findByUsername("testuser");
        assertThat(savedUser.isPresent()).isTrue();
        assertThat(savedUser.get().getLastName()).isEqualTo("User");
        assertThat(savedUser.get().getEmailAddress()).isNotNull();
    }


    /**
     * End-to-End Test if user is correctly updated in the Database.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldReturn404OnNotFound() throws JSONException, UserNotFoundException {
        // Request Setup
        final JSONObject requestBody = new JSONObject();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/testuser",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        // Verify correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // Verify correct function in UserController is called.
        verify(userController).updateUser(eq("testuser"), any());
    }


    /**
     * End-to-End Test if user is correctly found in the Database.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldReturnUser() throws JSONException, UserNotFoundException {
        // Save user object, so that user exists
        UserDbo existingUser = new UserDbo();
        existingUser.setId(1L);
        existingUser.setUsername("testuser");
        existingUser.setFirstName("Test");
        userRepository.save(existingUser);


        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/testuser",
                        HttpMethod.GET,
                        null,
                        String.class);

        // Verify correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify correct function in UserController is called.
        verify(userController).getUser(eq("testuser"));

        // Verify User is returned
        assertThat(response.getBody()).isNotNull();
    }


    /**
     * End-to-End Test if user is correctly found in the Database.
     */
    @Test
    public void shouldReturn404OnNotFoundUser() throws JSONException, UserNotFoundException {
        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/testuser",
                        HttpMethod.GET,
                        null,
                        String.class);

        // Verify correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // Verify correct function in UserController is called.
        verify(userController).getUser(eq("testuser"));

        // Verify User is returned
        assertThat(response.getBody()).isNull();
    }


    /**
     * End-to-End Test if user is correctly deleted from the Database.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldDeleteUser() throws UserNotFoundException {
        // Save user object, so that user exists
        UserDbo existingUser = new UserDbo();
        existingUser.setId(1L);
        existingUser.setUsername("testuser");
        existingUser.setFirstName("Test");
        userRepository.save(existingUser);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/testuser",
                        HttpMethod.DELETE,
                        null,
                        String.class);

        // Verify correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify correct function in UserController is called.
        verify(userController).deleteUser(eq("testuser"));

        // Verify User is deleted from Database.
        assertThat(userRepository.findByUsername("testuser").isPresent()).isFalse();
    }

    /**
     * End-to-End Test if user is correctly deleted from the Database.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldReturn404OnNotFoundWhileDelete() throws UserNotFoundException {
        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/testuser",
                        HttpMethod.DELETE,
                        null,
                        String.class);

        // Verify correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        // Verify correct function in UserController is called.
        verify(userController).deleteUser(eq("testuser"));
    }


}
