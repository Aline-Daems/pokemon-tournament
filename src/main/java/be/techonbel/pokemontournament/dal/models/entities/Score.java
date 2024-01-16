package be.techonbel.pokemontournament.dal.models.entities;

import jakarta.persistence.*;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long scoreId;

    private int victory;

    private int defeat;

    private int equality;
    @ManyToOne
    @JoinColumn(name="matchId")
    Match match;
}
