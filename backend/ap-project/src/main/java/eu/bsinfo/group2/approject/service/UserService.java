package eu.bsinfo.group2.approject.service;

import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UserAlreadyExistsException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.util.SuccessResult;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDbo createUser(UserDbo user) throws UserAlreadyExistsException;

    UserDbo updateUser(String username, UserDbo user) throws UserNotFoundException;

    SuccessResult deleteUser(String username) throws UserNotFoundException;

    Optional<UserDbo> findUser(String username);

    Optional<List<UserDbo>> getAllUser();
}
