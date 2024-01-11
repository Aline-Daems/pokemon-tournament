package be.techonbel.pokemontournament.dal.models.entities;

import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Arena {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long arenaId;
    private String city;
    private int nbPlayer;
    private int nbMinPlayer;
    private int nbMaxPlayer;
    private String type;
    private Status status;
    private int round;
    private Boolean womenOnly;
    private LocalDate closingDate;
    private  LocalDate creationDate;
    private  LocalDate updateDate;



}
