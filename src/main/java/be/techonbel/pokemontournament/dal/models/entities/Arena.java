package be.techonbel.pokemontournament.dal.models.entities;

import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Arena {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long arenaId;
    private String city;
    @Min(2)
    @Max(32)
    private int nbPlayer;
    private String type;
    @Enumerated(EnumType.STRING)
    private Status status = Status.pending;
    private int round;
    @Value("false")
    private Boolean womenOnly;
    private LocalDate closingDate;
    private LocalDate creationDate;
    private LocalDate updateDate;
    @ManyToMany(mappedBy = "arenas")
    private List<Player> players = new ArrayList<>();



}
