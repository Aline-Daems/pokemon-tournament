package be.techonbel.pokemontournament.pl.dtos;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public record ArenaDTO(
        Long arenaId,
        String city,

        int nbPlayer,
        String type,
        Status status,
        int round,
        LocalDate closingDate

        ) {

    public static ArenaDTO fromEntity(Arena arena){
        return  new ArenaDTO(arena.getArenaId(), arena.getCity(), arena.getNbPlayer(), arena.getType(), arena.getStatus(), arena.getRound(), arena.getClosingDate());
    }
}
