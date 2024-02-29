package be.techonbel.pokemontournament.dal.repositories;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
   Optional<Player> findByPseudoOrMail(String username, String mail);

   @Query("SELECT p.arenas FROM Player p WHERE p.pseudo = :pseudo")
   List<Arena> findArenasByPlayerPseudo(String pseudo);


}
