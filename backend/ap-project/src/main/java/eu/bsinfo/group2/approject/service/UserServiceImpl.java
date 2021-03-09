package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UserAlreadyExistsException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.repository.UserRepository;
import eu.bsinfo.group2.approject.util.PasswordService;
import eu.bsinfo.group2.approject.util.SuccessResult;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    @Override
    public UserDbo createUser(UserDbo user) throws UserAlreadyExistsException {
        if (userAlreadyExists(user.getUsername())) throw new UserAlreadyExistsException();
        else {
            user.setPassword(passwordService.encodePassword(user.getPassword()));
            return userRepository.save(user);
        }
    }

    @Override
    public UserDbo updateUser(String username, UserDbo user) throws UserNotFoundException {
        if (userAlreadyExists(username)) {
            UserDbo userObject = findUser(username).get();
            user.setId(userObject.getId());
            user.setPassword(userObject.getPassword());
            return userRepository.save(user);
        } else throw new UserNotFoundException();
    }

    @Override
    public SuccessResult deleteUser(String username) throws UserNotFoundException {
        if (userAlreadyExists(username)) {
            userRepository.deleteById(findUser(username).get().getId());
            return SuccessResult.SUCCESSFUL;
        } else throw new UserNotFoundException();
    }

    @Override
    public Optional<UserDbo> findUser(String username) {
        return userRepository.findByUsername(username);
    }

    private Boolean userAlreadyExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
