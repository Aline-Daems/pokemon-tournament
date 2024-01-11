package be.techonbel.pokemontournament.dal.models.entities;

import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long playerId;
    private String pseudo;
    private String mail;
    private String password;
    private LocalDate birthdate;
    private Gender gender;
    private int badges;


}
