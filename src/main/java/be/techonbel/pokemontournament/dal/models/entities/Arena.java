package be.techonbel.pokemontournament.dal.models.entities;

import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;

@Entity
@Data
public class Arena {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long arenaId;
    private String city;
    private int nbPlayer;
    private int nbMinPlayer;
    private int nbMaxPlayer;
    private String type;
    @Enumerated(EnumType.STRING)
    private Status status = Status.pending;
    private int round;
    private Boolean womenOnly;
    private LocalDate closingDate;
    private  LocalDate creationDate;
    private  LocalDate updateDate;


}
