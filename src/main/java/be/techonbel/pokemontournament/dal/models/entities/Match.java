package be.techonbel.pokemontournament.dal.models.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Match {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long matchId;
    private String pokemonPlayer;
    private String pokemonChampion;
    private String result;

    @ManyToOne
    @JoinColumn(name="player1Id")
    private Player player1;

    @ManyToOne
    @JoinColumn(name="player2Id")
    private Player player2;

    @ManyToOne
    @JoinColumn(name="arenaId")
    private Arena arena;



}
