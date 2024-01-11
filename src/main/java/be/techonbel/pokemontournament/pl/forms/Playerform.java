package be.techonbel.pokemontournament.pl.forms;

import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public record Playerform(

        String pseudo,
        String mail,
        String password,
        LocalDate birthdate,
        Gender gender,
        int badges,
        Roles role
) {
}
