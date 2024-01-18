package be.techonbel.pokemontournament.dal.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Score {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long scoreId;

    private int victory;

    private int defeat;

    private int equality;

    @ManyToOne
    @JoinColumn(name="player_Id")
    private Player player;

    private double points;


}
