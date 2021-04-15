package eu.bsinfo.group2.approject.entities.user;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@NoArgsConstructor
public class ContactTypes {

    @Id
    private String id;

    @OneToMany
    Set<ContactSet> contactTypes;

    ContactTypes(Set<ContactSet> contactTypes) {
        this.contactTypes = contactTypes;
    }
}

