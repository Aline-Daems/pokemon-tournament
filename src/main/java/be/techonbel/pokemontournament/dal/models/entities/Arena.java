package be.techonbel.pokemontournament.dal.models.entities;

import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
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
    private int nbMinPlayer;
    @Max(32)
    private int nbMaxPlayer;
    private int nbPlayer;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Status status = Status.pending;
    private int round;
    private int badgeMin;
    private int badgeMax;
    @Value("false")
    private Boolean womenOnly;
    private LocalDate closingDate;
    private LocalDate creationDate;
    private LocalDate updateDate;
    @ManyToMany(mappedBy = "arenas")
    private List<Player> players = new ArrayList<>();

    public Arena(Long arenaId, String city, int nbMinPlayer, int nbMaxPlayer, int nbPlayer, Category category, Status status, int round, int badgeMin, int badgeMax, Boolean womenOnly, LocalDate closingDate, LocalDate creationDate, LocalDate updateDate) {
        this.arenaId = arenaId;
        this.city = city;
        this.nbMinPlayer = nbMinPlayer;
        this.nbMaxPlayer = nbMaxPlayer;
        this.nbPlayer = nbPlayer;
        this.category = category;
        this.status = status;
        this.round = round;
        this.badgeMin = badgeMin;
        this.badgeMax = badgeMax;
        this.womenOnly = womenOnly;
        this.closingDate = closingDate;
        this.creationDate = creationDate;
        this.updateDate = updateDate;

    }

    public Arena() {

    }

    public void incrementNbPlayer(){


        this.nbPlayer++;
    }

}
