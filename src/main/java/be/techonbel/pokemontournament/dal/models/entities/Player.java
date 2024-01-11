package be.techonbel.pokemontournament.dal.models.entities;

import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;

@Entity
@Data
public class Player {

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
    private Roles role;


}
