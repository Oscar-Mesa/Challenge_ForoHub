package challenge.hub.api.domain.profile;


import challenge.hub.api.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;

import java.util.List;
import java.util.Set;

@Table(name = "tbl_profile")
@Entity(name = "profile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "profiles",fetch = FetchType.EAGER)
    private Set<User> users;

    public Profile(ProfileDTO profileDTO){
        this.name = profileDTO.name();
    }
}
