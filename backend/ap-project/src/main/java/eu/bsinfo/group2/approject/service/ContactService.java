package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.entities.user.UserDbo;

import java.util.Set;

public interface ContactService {
    Set<ContactSet> setContactData(UserDbo user, Set<ContactSet> contactInformation);
}
