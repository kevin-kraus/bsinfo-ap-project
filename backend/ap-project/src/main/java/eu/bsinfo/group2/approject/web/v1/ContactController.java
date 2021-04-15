package eu.bsinfo.group2.approject.web.v1;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.exception.ContactSetNotFoundException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.service.ContactService;
import eu.bsinfo.group2.approject.util.SuccessResult;
import eu.bsinfo.group2.approject.web.v1.interfaces.ContactApi;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContactController extends BaseAbstractController implements ContactApi {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ContactSet addContactSet(String username, ContactSet contactInformation) throws UserNotFoundException {
        return contactService.addContactData(username, contactInformation);
    }

    @Override
    public List<ContactSet> getUserContactSets(String username) throws UserNotFoundException {
        return contactService.getAllUserContactSets(username);
    }

    @Override
    public ContactSet updateContactSet(String username, Long contactSetId) throws UserNotFoundException, ContactSetNotFoundException {
        return contactService.updateContactSet(username, contactSetId);
    }

    @Override
    public SuccessResult deleteContactSet(String username, Long contactSetId) throws UserNotFoundException, ContactSetNotFoundException {
        return contactService.deleteContactSet(username, contactSetId);
    }
}
