package eu.bsinfo.group2.approject.web.v1;

import eu.bsinfo.group2.approject.ApProjectApplication;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UnauthorizedException;
import eu.bsinfo.group2.approject.exception.UserAlreadyExistsException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.repository.UserRepository;
import eu.bsinfo.group2.approject.util.PasswordService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@Tag("IntegrationTest")
@SpringBootTest(
        classes = {ApProjectApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class LoginControllerIT {

    @SpyBean
    private LoginController loginController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Helper function.
     *
     * @return JSON Request Body of a userDbo
     */
    private JSONObject generateRequestBody() throws JSONException {
        final JSONObject requestBody = new JSONObject();
        requestBody.put("username", "testuser");
        requestBody.put("password", "testpassword");
        return requestBody;
    }


    /**
     * End-to-End Test if user is correctly authenticated.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldAuthenticateUser() throws JSONException, UserAlreadyExistsException, UnauthorizedException, UserNotFoundException {
        UserDbo user = new UserDbo();
        user.setUsername("testuser");
        user.setPassword(passwordService.encodePassword("testpassword"));
        userRepository.save(user);

        // Request Setup
        JSONObject requestBody = generateRequestBody();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/login",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        // Verify correct function in UserController is called.
        verify(loginController).authenticateUser(any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /**
     * End-to-End Test if user is correctly not authenticated if password is wrong.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldReturn401IfWrongPassword() throws JSONException, UserAlreadyExistsException, UnauthorizedException, UserNotFoundException {
        UserDbo user = new UserDbo();
        user.setUsername("testuser");
        user.setPassword(passwordService.encodePassword("testPassword"));
        userRepository.save(user);

        // Request Setup
        JSONObject requestBody = generateRequestBody();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/login",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        // Verify correct function in UserController is called.
        verify(loginController).authenticateUser(any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    /**
     * End-to-End Test if user is not found.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldReturn404OnNonExistingUser() throws JSONException, UserAlreadyExistsException, UnauthorizedException, UserNotFoundException {
        // Request Setup
        JSONObject requestBody = generateRequestBody();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/login",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        // Verify correct function in UserController is called.
        verify(loginController).authenticateUser(any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}

