package be.techonbel.pokemontournament.dal.repositories;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArenaRepository extends JpaRepository<Arena, Long> {

    List<Arena> findTop10AllByOrderByUpdateDateDesc();

    Optional<Arena> findByArenaId(Long id);



}
