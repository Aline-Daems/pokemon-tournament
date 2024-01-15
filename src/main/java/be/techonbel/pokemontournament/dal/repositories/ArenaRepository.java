package be.techonbel.pokemontournament.dal.repositories;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArenaRepository extends JpaRepository<Arena, Long> {

    List<Arena> findTop10AllByOrderByUpdateDateDesc();
    @Query("SELECT a from Arena a LEFT JOIN FETCH  a.players WHERE a.arenaId = :arenaId")
    Optional<Arena> findByArenaIdWithPlayer(@Param("arenaId") Long id);





}
