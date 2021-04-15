package eu.bsinfo.group2.approject.repository;

import eu.bsinfo.group2.approject.entities.user.UserDbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDbo, Long> {
    Optional<UserDbo> findById(Long id);

    Optional<UserDbo> findByUsername(String username);

    Optional<List<UserDbo>> getAllUser();
}
