package be.techonbel.pokemontournament.pl.dtos;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record PlayerDTOAll(
        Long playerId,
        String pseudo,
        String mail,
        String password,
        LocalDate birthdate,
        Gender gender,
        int badges,
        Category category,
        List<Roles> role,
        List<Arena> arenas
) {

    public static PlayerDTOAll fromBLL(Player player) {
        return  new PlayerDTOAll(
                player.getPlayerId(),
                player.getPseudo(),
                player.getPassword(),
                player.getMail(),
                player.getBirthdate(),
                player.getGender(),
                player.getBadges(),
                player.getCategory(),
                player.getRole(),
                player.getArenas()
        );
    }
}
