package be.techonbel.pokemontournament.dal.repositories;

import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    Optional<Score> findByPlayer(Player player);
}
