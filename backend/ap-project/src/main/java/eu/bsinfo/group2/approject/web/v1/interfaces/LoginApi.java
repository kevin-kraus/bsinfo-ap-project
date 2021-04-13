package eu.bsinfo.group2.approject.web.v1.interfaces;

import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UnauthorizedException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.util.LoginFormInput;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoginApi {
    /**
     * Validates user credentials.
     *
     * @param loginInfo Username and Password in JSON Format
     * @return UserDBO if Authorization is successful.
     * @throws UnauthorizedException if Authorization is unsuccessful.
     */
    @PostMapping(value = "/api/v1/login")
    UserDbo authenticateUser(@RequestBody LoginFormInput loginInfo) throws UnauthorizedException, UserNotFoundException;
}
