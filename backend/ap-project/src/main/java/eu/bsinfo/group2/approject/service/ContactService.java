package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.exception.ContactSetNotFoundException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.util.SuccessResult;

import java.util.List;

public interface ContactService {
    ContactSet addContactData(String username, ContactSet contactInformation) throws UserNotFoundException;

    List<ContactSet> getAllUserContactSets(String username) throws UserNotFoundException;

    ContactSet updateContactSet(String username, Long contactSetId, ContactSet update) throws UserNotFoundException, ContactSetNotFoundException;

    SuccessResult deleteContactSet(String username, Long contactSetId) throws UserNotFoundException, ContactSetNotFoundException;
}
