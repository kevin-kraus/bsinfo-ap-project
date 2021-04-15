package eu.bsinfo.group2.approject.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "contactsets")
public class ContactSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    // Prevent overfetching.
    @JsonIgnore
    private UserDbo user;

    private ContactType contactType;

    private String value;

}

