package be.techonbel.pokemontournament.pl.dtos;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;

public record ArenaDTO(
        Long arenaId,
        String city,
        int nbMinPlayer,
        int nbMaxPlayer,
        int nbPlayer,
        Status status,
        int round,
        int badgeMin,
        int badgeMax,
        LocalDate closingDate,
        List<PlayerDTO>  player,
        Category category)

         {

    public static ArenaDTO fromEntity(Arena arena){
        return  new ArenaDTO(arena.getArenaId(), arena.getCity(), arena.getNbPlayer(), arena.getNbMinPlayer(), arena.getNbMaxPlayer(), arena.getStatus(), arena.getRound(), arena.getBadgeMin(), arena.getBadgeMax(), arena.getClosingDate(), arena.getPlayers().stream().map(PlayerDTO::fromEntity).toList(), arena.getCategory());
    }
}
