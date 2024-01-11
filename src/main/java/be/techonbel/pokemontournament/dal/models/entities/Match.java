package be.techonbel.pokemontournament.dal.models.entities;

import jakarta.persistence.*;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long matchId;
    private String pokemonPlayer;
    private String pokemonChampion;
    private String result;

    @ManyToOne
    @JoinColumn(name="playerId")
    private Player player;

    @ManyToOne
    @JoinColumn(name="arenaId")
    private Arena arena;



}
