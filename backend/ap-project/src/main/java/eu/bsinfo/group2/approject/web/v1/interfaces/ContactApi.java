package eu.bsinfo.group2.approject.web.v1.interfaces;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public interface ContactApi {

    /**
     * Adds contact information to an user
     *
     * @param username The username of the user to add contact-information
     * @return a set of contact information.
     */
    @PostMapping(value = "/api/v1/user/contact/{username}")
    Set<ContactSet> setContactData(@PathVariable("username") String username, @RequestBody Set<ContactSet> contactInformation) throws UserNotFoundException;
}
