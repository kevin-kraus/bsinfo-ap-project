package eu.bsinfo.group2.approject.entities.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private UserType userType;

    @OneToMany
    private Set<ContactSet> contactTypes;

    @CreationTimestamp
    private Date generatedAt;

    @UpdateTimestamp
    private Date updatedAt;
}
