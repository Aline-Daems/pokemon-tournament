package be.techonbel.pokemontournament.bl.services.impl;

import be.techonbel.pokemontournament.bl.services.ScoreService;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.Score;
import be.techonbel.pokemontournament.dal.repositories.PlayerRepository;
import be.techonbel.pokemontournament.dal.repositories.ScoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;

    private final PlayerRepository playerRepository;

    public ScoreServiceImpl(ScoreRepository scoreRepository, PlayerRepository playerRepository) {
        this.scoreRepository = scoreRepository;
        this.playerRepository = playerRepository;
    }


    @Override
    public Score getOne(Long id) {
      return  scoreRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Score> getAll() {
        return scoreRepository.findAll(Sort.by(Sort.Order.desc("points")));
    }

    @Override
    public void incrementVictory(Long id) {


        Score score =   scoreRepository.getOne(id);
        score.setVictory(score.getVictory()+1);
        score.setPoints(score.getPoints()+1);
        scoreRepository.save(score);
    }

    @Override
    public void incrementDefeat(Long id) {

        Score score =   scoreRepository.getOne(id);
        score.setDefeat(score.getDefeat()+1);
        scoreRepository.save(score);
    }

    @Override
    public void incrementEquality(Long id) {

        Score score =   scoreRepository.getOne(id);
        score.setEquality(score.getEquality()+1);
        score.setPoints(score.getPoints()+0.5f);
        scoreRepository.save(score);
    }
}
