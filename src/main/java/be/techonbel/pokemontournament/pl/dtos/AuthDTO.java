package be.techonbel.pokemontournament.pl.dtos;

import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import lombok.Builder;
import lombok.Data;
import org.aspectj.weaver.patterns.IToken;

import java.util.List;


@Builder
public record AuthDTO(

        String pseudo,
        String Token,
        List<Roles> roles

) {

    public static AuthDTO create(String token, String login, List<Roles> roles) {
        return new AuthDTO(token, login, roles);
    }
}
