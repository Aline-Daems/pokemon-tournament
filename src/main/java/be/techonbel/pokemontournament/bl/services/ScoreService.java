package be.techonbel.pokemontournament.bl.services;

import be.techonbel.pokemontournament.dal.models.entities.Score;

import java.util.List;

public interface ScoreService {


    Score getOne(Long id);
    List<Score> getAll();

    void incrementVictory(Long id);

    void incrementDefeat( Long id);

    void incrementEquality( Long id);


}
