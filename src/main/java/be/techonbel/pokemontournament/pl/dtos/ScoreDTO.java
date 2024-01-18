package be.techonbel.pokemontournament.pl.dtos;

import java.util.stream.Collectors;

import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.Score;

import java.util.List;

public record ScoreDTO(

        Long scoreId,
        int victory,
        int defeat,
        int equality,
        double points,
        String player
) {

    public static ScoreDTO fromEntity (Score score) {
        return new ScoreDTO(score.getScoreId(), score.getVictory(), score.getDefeat(), score.getEquality(), score.getPoints(), score.getPlayer().getPseudo());
    }
}
