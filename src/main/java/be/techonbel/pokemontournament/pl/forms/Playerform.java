package be.techonbel.pokemontournament.pl.forms;

import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.List;

public record Playerform(
        String pseudo,
        String mail,
        String password,
        LocalDate birthdate,
        Gender gender,
        int badges,
        List<Roles>  role,
        List<Long> arenaId
) {
}
