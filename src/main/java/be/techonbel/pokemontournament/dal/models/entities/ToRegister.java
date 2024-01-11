package be.techonbel.pokemontournament.dal.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ToRegister {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="playerId")
    private Player player;

    @ManyToOne
    @JoinColumn(name="arenaId")
    private Arena arena;


}
