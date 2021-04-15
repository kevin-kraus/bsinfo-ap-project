package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.ContactSetNotFoundException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.repository.ContactSetRepository;
import eu.bsinfo.group2.approject.repository.UserRepository;
import eu.bsinfo.group2.approject.util.SuccessResult;
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

    @Override
    public ContactSet updateContactSet(String username, Long contactSetId) throws UserNotFoundException, ContactSetNotFoundException {
        Optional<UserDbo> user = userRepository.findByUsername(username);
        ContactSet contactSet;
        if (user.isPresent()) {
            if (contactSetRepository.findByContactId(contactSetId) != null) {
                contactSet = contactSetRepository.findByContactId(contactSetId);
            } else {
                throw new ContactSetNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }
        return contactSet;
    }

    @Override
    public SuccessResult deleteContactSet(String username, Long contactSetId) throws UserNotFoundException, ContactSetNotFoundException {
        Optional<UserDbo> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            if (contactSetRepository.findByContactId(contactSetId) != null) {
                contactSetRepository.deleteById(contactSetId);
                return SuccessResult.SUCCESSFUL;
            } else {
                throw new ContactSetNotFoundException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }
}
