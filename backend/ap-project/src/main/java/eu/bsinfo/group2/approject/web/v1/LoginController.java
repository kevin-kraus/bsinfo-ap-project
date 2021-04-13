package eu.bsinfo.group2.approject.web.v1;

import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UnauthorizedException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.repository.UserRepository;
import eu.bsinfo.group2.approject.util.LoginFormInput;
import eu.bsinfo.group2.approject.util.PasswordService;
import eu.bsinfo.group2.approject.web.v1.interfaces.LoginApi;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController extends BaseAbstractController implements LoginApi {

    private final PasswordService passwordService;
    private final UserRepository userRepository;

    public LoginController(PasswordService passwordService, UserRepository userRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
    }


    @Override
    public UserDbo authenticateUser(LoginFormInput loginInfo) throws UnauthorizedException, UserNotFoundException {
        Optional<UserDbo> user = userRepository.findByUsername(loginInfo.getUsername());
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        UserDbo userDbo = user.get();
        boolean isAuthenticated = passwordService.verifyPassword(loginInfo.getPassword(), userDbo.getPassword());

        if (isAuthenticated) {
            return userDbo;
        } else {
            throw new UnauthorizedException();
        }
    }
}
