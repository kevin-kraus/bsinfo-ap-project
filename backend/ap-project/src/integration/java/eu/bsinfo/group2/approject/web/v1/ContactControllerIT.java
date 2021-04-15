package eu.bsinfo.group2.approject.web.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bsinfo.group2.approject.ApProjectApplication;
import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.entities.user.ContactType;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.ContactSetNotFoundException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.repository.ContactSetRepository;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@Tag("IntegrationTest")
@SpringBootTest(
        classes = {ApProjectApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
public class ContactControllerIT {

    @SpyBean
    private ContactController contactController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactSetRepository contactSetRepository;

    /**
     * Helper function to create Request body
     *
     * @return Request body.
     */
    private JSONObject generateRequestBody() throws JSONException {
        final JSONObject requestBody = new JSONObject();
        requestBody.put("contactType", "SKYPE");
        requestBody.put("value", "skypeusername");
        return requestBody;
    }

    private UserDbo addTestUser() {
        UserDbo user = new UserDbo();
        user.setId(1L);
        user.setUsername("kkraus");
        userRepository.save(user);
        return user;
    }


    /**
     * End-to-End Test if contactSet is correctly created.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void shouldCreateContactSet() throws JSONException, UserNotFoundException {
        addTestUser();
        // Request Setup
        JSONObject requestBody = generateRequestBody();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).addContactSet(eq("kkraus"), any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify contactSet is correctly added to the Database.
        List<ContactSet> savedContacts = contactSetRepository.findByUserId(1L);
        assertThat(savedContacts.size()).isEqualTo(1);
    }

    /**
     * End-to-End Test if on creation request returns 404 if user is not found.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void createShouldReturn404() throws JSONException, UserNotFoundException {
        // Request Setup
        JSONObject requestBody = generateRequestBody();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).addContactSet(eq("kkraus"), any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * End-to-End Test if request returns 404 if user is not found (GET).
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void getShouldReturn404() throws JSONException, UserNotFoundException {
        // Request Setup
        JSONObject requestBody = generateRequestBody();
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus",
                        HttpMethod.GET,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).getUserContactSets(eq("kkraus"));

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * End-to-End Test if request returns all ContactSets of a user.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void getShouldReturnAllContactSets() throws UserNotFoundException, JsonProcessingException {
        UserDbo user = addTestUser();
        // Save example entry.
        ContactSet contactSet = new ContactSet();
        contactSet.setContactType(ContactType.EMAIL);
        contactSet.setValue("AAA");
        contactSet.setUser(user);
        contactSetRepository.save(contactSet);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus",
                        HttpMethod.GET,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).getUserContactSets(eq("kkraus"));

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);


        ObjectMapper mapper = new ObjectMapper();
        List<ContactSet> userDboList = mapper.readValue(response.getBody(), new TypeReference<>() {
        });
        assertThat(userDboList.size()).isEqualTo(1);
    }

    /**
     * End-to-End Test if request overwrites ContactSet of a user.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void putShouldUpdate() throws JSONException, UserNotFoundException, ContactSetNotFoundException {
        UserDbo user = addTestUser();
        // Save example entry.
        ContactSet contactSet = new ContactSet();
        contactSet.setContactType(ContactType.SKYPE);
        contactSet.setValue("AAA");
        contactSet.setUser(user);
        contactSet = contactSetRepository.save(contactSet);

        JSONObject requestBody = generateRequestBody();
        requestBody.put("value", "ABC");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus/" + contactSet.getId(),
                        HttpMethod.PUT,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).updateContactSet(eq("kkraus"), eq(contactSet.getId()), any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(contactSetRepository.findById(contactSet.getId()).get().getValue()).isEqualTo("ABC");
    }


    /**
     * End-to-End Test if request returns 404 if user is not found.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void putShouldReturn404IfUserNotFound() throws JSONException, UserNotFoundException, ContactSetNotFoundException {
        JSONObject requestBody = generateRequestBody();
        requestBody.put("value", "ABC");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus/" + 1,
                        HttpMethod.PUT,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).updateContactSet(eq("kkraus"), eq(1L), any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * End-to-End Test if request returns 404 if ContactSet is not found.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void putShouldReturn404IfContactSetNotFound() throws JSONException, UserNotFoundException, ContactSetNotFoundException {
        addTestUser();

        JSONObject requestBody = generateRequestBody();
        requestBody.put("value", "ABC");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus/" + 1,
                        HttpMethod.PUT,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).updateContactSet(eq("kkraus"), eq(1L), any());

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    /**
     * End-to-End Test if request deletes ContactSet of a user.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void deleteShouldDeleteContactSet() throws JSONException, UserNotFoundException, ContactSetNotFoundException {
        UserDbo user = addTestUser();
        // Save example entry.
        ContactSet contactSet = new ContactSet();
        contactSet.setContactType(ContactType.SKYPE);
        contactSet.setValue("AAA");
        contactSet.setUser(user);
        contactSet = contactSetRepository.save(contactSet);

        JSONObject requestBody = generateRequestBody();
        requestBody.put("value", "ABC");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus/" + contactSet.getId(),
                        HttpMethod.DELETE,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).deleteContactSet(eq("kkraus"), eq(contactSet.getId()));

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify it was deleted from the database
        assertThat(contactSetRepository.findById(1L).isPresent()).isFalse();
    }

    /**
     * returnes 404 if user is not found
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void deleteShouldReturn404IfUserNotFound() throws JSONException, UserNotFoundException, ContactSetNotFoundException {
        JSONObject requestBody = generateRequestBody();
        requestBody.put("value", "ABC");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus/" + 1,
                        HttpMethod.DELETE,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).deleteContactSet(eq("kkraus"), eq(1L));

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * End-to-End Test if request returns 404 if ContactSet is not found.
     */
    @Test
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void deleteShouldReturn404IfContactSetNotFound() throws JSONException, UserNotFoundException, ContactSetNotFoundException {
        addTestUser();
        JSONObject requestBody = generateRequestBody();
        requestBody.put("value", "ABC");
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");
        final HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("/api/v1/user/contact/kkraus/" + 1,
                        HttpMethod.DELETE,
                        requestEntity,
                        String.class);

        // Verify correct function in ContactController is called.
        verify(contactController).deleteContactSet(eq("kkraus"), eq(1L));

        // Verify Request returns correct HTTP status code.
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


}
