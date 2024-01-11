package be.techonbel.pokemontournament.pl.forms;

import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record ArenaForm(
        String city,
        int nbPlayer,
        int nbMinPlayer,
        int nbMaxPlayer,
        String type,
        Status status,
        int round,
        Boolean womenOnly,
        LocalDate closingDate,
        LocalDate creationDate,
        LocalDate updateDate
) {
}
