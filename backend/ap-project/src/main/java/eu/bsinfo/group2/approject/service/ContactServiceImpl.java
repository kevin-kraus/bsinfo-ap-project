package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.repository.ContactSetRepository;
import eu.bsinfo.group2.approject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final UserRepository userRepository;
    private final ContactSetRepository contactSetRepository;

    public ContactServiceImpl(UserRepository userRepository, ContactSetRepository contactSetRepository) {
        this.userRepository = userRepository;
        this.contactSetRepository = contactSetRepository;
    }

    @Override
    public ContactSet addContactData(String username, ContactSet contactInformation) throws UserNotFoundException {
        Optional<UserDbo> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserDbo userDbo = user.get();
            contactInformation.setUser(userDbo);
            return contactSetRepository.save(contactInformation);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<ContactSet> getAllUserContactSets(String username) throws UserNotFoundException {
        Optional<UserDbo> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserDbo userDbo = user.get();
            return contactSetRepository.findByUserId(userDbo.getId());
        } else {
            throw new UserNotFoundException();
        }
    }
}
