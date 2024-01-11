package be.techonbel.pokemontournament.dal.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long scoreId;

    private int victory;

    private int defeat;

    private int equality;

    // TODO: 11-01-24
    // Faire en sorte sur les int ici fasse +1 en fonction du result dans Match ( voir aeroport projet)
}
