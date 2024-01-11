package be.techonbel.pokemontournament.dal.repositories;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArenaRepository extends JpaRepository<Arena, Long> {
}
