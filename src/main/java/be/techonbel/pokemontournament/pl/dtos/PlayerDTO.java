package be.techonbel.pokemontournament.pl.dtos;

import be.techonbel.pokemontournament.dal.models.entities.Player;

public record PlayerDTO(
        Long id,
         String pseudo
) {

    public static PlayerDTO fromEntity(Player player){
        return  new PlayerDTO(player.getPlayerId(), player.getPseudo());
    }
}
