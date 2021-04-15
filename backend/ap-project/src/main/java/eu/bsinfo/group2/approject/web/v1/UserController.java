package eu.bsinfo.group2.approject.web.v1;

import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UserAlreadyExistsException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.service.UserService;
import eu.bsinfo.group2.approject.util.SuccessResult;
import eu.bsinfo.group2.approject.web.v1.interfaces.UserApi;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController extends BaseAbstractController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDbo getUser(String username) throws UserNotFoundException {
        Optional<UserDbo> user = userService.findUser(username);
        if (user.isEmpty()) throw new UserNotFoundException();
        else return user.get();
    }
    // TODO: implement getAllUsers
    @Override
    public Optional<List<UserDbo>> getAllUsers() {
        return userService.getAllUser();
    }

    @Override
    public UserDbo createUser(UserDbo userDbo) throws UserAlreadyExistsException {
        return userService.createUser(userDbo);
    }

    @Override
    public UserDbo updateUser(String username, UserDbo userDbo) throws UserNotFoundException {
        return userService.updateUser(username, userDbo);
    }

    @Override
    public SuccessResult deleteUser(String username) throws UserNotFoundException {
        return userService.deleteUser(username);
    }
}
