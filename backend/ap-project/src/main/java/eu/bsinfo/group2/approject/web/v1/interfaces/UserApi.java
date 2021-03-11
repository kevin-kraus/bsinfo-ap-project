package eu.bsinfo.group2.approject.web.v1.interfaces;

import eu.bsinfo.group2.approject.entities.user.UserDbo;
import eu.bsinfo.group2.approject.exception.UserAlreadyExistsException;
import eu.bsinfo.group2.approject.exception.UserNotFoundException;
import eu.bsinfo.group2.approject.util.SuccessResult;
import org.springframework.web.bind.annotation.*;

@RestController
public interface UserApi {
    /**
     * Gets a User from the Database if it exists.
     *
     * @param username The username of the user to be found.
     * @return User
     */
    @GetMapping(value = "/api/v1/user/{username}")
    UserDbo getUser(@PathVariable("username") String username) throws UserNotFoundException;

    /**
     * Creates a new User in the Database.
     *
     * @param userDbo The User to be created.
     * @return User which was created.
     */
    @PutMapping(value = "/api/v1/user")
    UserDbo createUser(@RequestBody UserDbo userDbo) throws UserAlreadyExistsException;

    /**
     * Updates a existing User.
     *
     * @param userDbo The User to be updated.
     * @return The updated User.
     */
    @PostMapping(value = "/api/v1/user/{username}")
    UserDbo updateUser(@PathVariable("username") String username, @RequestBody UserDbo userDbo) throws UserNotFoundException;

    /**
     * Deletes a User from the Database
     *
     * @param username The Username of the User, which should be deleted.
     * @return "SUCCESSFUL" if the deletion worked.
     */
    @DeleteMapping(value = "/api/v1/user/{username}")
    SuccessResult deleteUser(@PathVariable("username") String username) throws UserNotFoundException;
}
