package be.techonbel.pokemontournament.pl.forms;

import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;

public record ArenaForm(
        String city,
        int nbMinPlayer,

        int nbMaxPlayer,
        int nbPlayer,
        String type,
        Status status,
        int round,
        int badgeMin,
        int badgeMax,
        Boolean womenOnly,
        LocalDate creationDate,
        LocalDate updateDate,
        List<Long> playerId
) {
}
