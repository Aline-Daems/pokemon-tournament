package be.techonbel.pokemontournament.dal.repositories;

import be.techonbel.pokemontournament.dal.models.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
