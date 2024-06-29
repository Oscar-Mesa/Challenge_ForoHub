package challenge.hub.api.domain.user;


import challenge.hub.api.domain.profile.Profile;
import challenge.hub.api.domain.profile.ProfileDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "tbl_user")
@Entity(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "tbl_user_profile", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id")
    )
    private Set<Profile> profiles;

    public User(UserDTO userDTO){
        if(userDTO == null){
            throw new IllegalArgumentException("Data not valid");
        }

        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();

        this.profiles = new HashSet<>();

        if(userDTO.profiles() != null){
            for (ProfileDTO profileDTO : userDTO.profiles()){
                this.profiles.add(new Profile(profileDTO));
            }
        }

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return profiles;
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }
}
