package eu.bsinfo.group2.approject.web.v1.interfaces;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public interface ContactApi {

    /**
     * Adds contact information to an user
     *
     * @param username The username of the user to add contact-information
     * @return a set of contact information.
     */
    @PostMapping(value = "/api/v1/user/contact/{username}")
    ContactSet addContactSet(@PathVariable("username") String username, @RequestBody ContactSet contactInformation) throws UserNotFoundException;

    @GetMapping(value = "/api/v1/user/contact/{username}")
    List<ContactSet> getUserContactSets(@PathVariable("username") String username) throws UserNotFoundException;

}
