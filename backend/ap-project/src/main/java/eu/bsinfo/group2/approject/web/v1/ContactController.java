package eu.bsinfo.group2.approject.web.v1;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.service.ContactService;
import eu.bsinfo.group2.approject.service.UserService;
import eu.bsinfo.group2.approject.web.v1.interfaces.ContactApi;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
public class ContactController extends BaseAbstractController implements ContactApi {

    private final UserService userService;
    private final ContactService contactService;

    public ContactController (UserService userService, ContactService contactService) {
        this.userService = userService;
        this.contactService = contactService;
    }

    @Override
    public Set<ContactSet> setContactData(String username, Set<ContactSet> contactInformation) throws UserNotFoundException {
        Optional<UserDbo> user = userService.findUser(username);
        if (user.isEmpty()) throw new UserNotFoundException();
        return contactService.setContactData(user.get(), contactInformation);
    }
}
