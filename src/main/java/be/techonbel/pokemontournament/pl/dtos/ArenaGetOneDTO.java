package be.techonbel.pokemontournament.pl.dtos;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;

import java.time.LocalDate;
import java.util.List;

public class ArenaGetOneDTO {
    Long playerId;
    String pseudo;


    public ArenaGetOneDTO(Long playerId, String pseudo) {
        this.playerId = playerId;
        this.pseudo = pseudo;
    }
}