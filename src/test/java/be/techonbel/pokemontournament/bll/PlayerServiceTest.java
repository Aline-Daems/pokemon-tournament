package be.techonbel.pokemontournament.bll;

import be.techonbel.pokemontournament.bl.services.impl.PlayerServiceImpl;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;


import be.techonbel.pokemontournament.dal.repositories.PlayerRepository;
import be.techonbel.pokemontournament.pl.dtos.PlayerDTOAll;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @Mock
    private PlayerRepository playerRepository;
    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player player;

    private PlayerDTOAll playerDTO;

    @BeforeEach
    void setUp() {

        List<Roles> roles = Arrays.asList(Roles.challenger);
        Arena arena1 = new Arena(1L, "String", 2, 4, 2, Category.Junior, Status.pending, 0, 0, 0, true, LocalDate.now(), LocalDate.now(), LocalDate.now());
        List<Arena> arenas = Arrays.asList(arena1);

        playerDTO = new PlayerDTOAll(1L, "String850", "email@string.com", "string", LocalDate.now(), Gender.female, 1, Category.Junior, roles, arenas);
        player = new Player(playerDTO.playerId(), playerDTO.pseudo(), playerDTO.mail(), playerDTO.password(), playerDTO.birthdate(), playerDTO.gender(), playerDTO.badges(), playerDTO.category(), playerDTO.role(), playerDTO.arenas());

    }

    @Test
    public void getById() {

        //Arrange

        when(playerRepository.findByPlayerId(anyLong())).thenReturn(Optional.of(player));

        Optional<Player> search = playerService.getOne(1L);

        assertTrue(search.isPresent());
        assertEquals(player, search.get());
    }
}
