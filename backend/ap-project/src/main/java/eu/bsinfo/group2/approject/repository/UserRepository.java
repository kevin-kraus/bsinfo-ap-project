package eu.bsinfo.group2.approject.repository;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import eu.bsinfo.group2.approject.entities.user.UserDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserDbo, Long> {
    Optional<UserDbo> findById(Long id);

    Optional<UserDbo> findByUsername(String username);
    Set<ContactSet> saveContactData(Optional<UserDbo> userDbo, Set<ContactSet> contactInformation);
}
