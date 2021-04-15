package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ContactServiceImpl implements ContactService {

    private final UserRepository userRepository;

    public ContactServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<ContactSet> setContactData(UserDbo user, Set<ContactSet> contactInformation) {
        Optional<UserDbo> username = userRepository.findByUsername(user.getUsername());
        return userRepository.saveContactData(username, contactInformation);
    }
}
