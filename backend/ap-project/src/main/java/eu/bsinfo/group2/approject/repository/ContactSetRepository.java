package eu.bsinfo.group2.approject.repository;

import eu.bsinfo.group2.approject.entities.user.ContactSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactSetRepository extends JpaRepository<ContactSet, Long> {
    List<ContactSet> findByUserId(Long userId);
}
