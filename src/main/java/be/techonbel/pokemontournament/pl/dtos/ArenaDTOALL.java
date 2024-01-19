package be.techonbel.pokemontournament.pl.dtos;

import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;

import java.time.LocalDate;
import java.util.List;

public record ArenaDTOALL (Long arenaId,
                           String city,
                           int nbMinPlayer,
                           int nbMaxPlayer,
                           int nbPlayer,
                           Category category,
                           Status status,

                           int round,
                           int badgeMin,
                           int badgeMax,

                           Boolean womenOnly,
                           LocalDate closingDate,

                           LocalDate crationDate,

                           LocalDate updateDate,
                           List<PlayerDTO> player
                           ){
}
