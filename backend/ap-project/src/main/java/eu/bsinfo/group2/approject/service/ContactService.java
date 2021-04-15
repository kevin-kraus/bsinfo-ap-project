package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;

import java.util.List;

public interface ContactService {
    ContactSet addContactData(String username, ContactSet contactInformation) throws UserNotFoundException;

    List<ContactSet> getAllUserContactSets(String username) throws UserNotFoundException;
}
