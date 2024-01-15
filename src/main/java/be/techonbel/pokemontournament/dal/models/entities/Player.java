package be.techonbel.pokemontournament.dal.models.entities;

import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class Player  implements UserDetails {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long playerId;
    @Column(unique = true)
    private String pseudo;
    @Column(unique = true)
    private String mail;
    private String password;
    private LocalDate birthdate;
    private Gender gender;
    @Value("0")
    private int badges;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(value = EnumType.STRING)
    private List<Roles> role;
    @ManyToMany
    @JoinTable(name="register", joinColumns = @JoinColumn(name="arenaId"), inverseJoinColumns = @JoinColumn(name="playerId"))
    private List<Arena> arenas = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream()
                .map(roles -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return pseudo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
