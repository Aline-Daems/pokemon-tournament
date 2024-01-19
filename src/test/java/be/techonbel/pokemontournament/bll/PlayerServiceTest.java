package be.techonbel.pokemontournament.bll;

import be.techonbel.pokemontournament.bl.services.impl.PlayerServiceImpl;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;


import be.techonbel.pokemontournament.dal.repositories.ArenaRepository;
import be.techonbel.pokemontournament.dal.repositories.PlayerRepository;
import be.techonbel.pokemontournament.pl.dtos.PlayerDTOAll;

import be.techonbel.pokemontournament.pl.forms.Playerform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {
    @InjectMocks
    private PlayerServiceImpl playerService;
    @Mock
    private PlayerRepository playerRepository;


    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ArenaRepository arenaRepository;


    private Player player;

    private PlayerDTOAll playerDTO;

    private Playerform form;

    private Arena arena1;

    @BeforeEach
    void setUp() {

        List<Roles> roles = Arrays.asList(Roles.challenger);
        arena1 = new Arena(1L, "String", 2, 4, 2, Category.Junior, Status.pending, 0, 1, 3, true, LocalDate.now().plusDays(5), LocalDate.now(), LocalDate.now());
        List<Arena> arenas = Arrays.asList(arena1);
        List<Long> arenaId = List.of(1L);
        playerDTO = new PlayerDTOAll(1L, "String850", "email@string.com", "string", LocalDate.now(), Gender.female, 2, Category.Junior, roles, arenas);
        player = new Player(playerDTO.playerId(), playerDTO.pseudo(), playerDTO.mail(), playerDTO.password(), playerDTO.birthdate(), playerDTO.gender(), playerDTO.badges(), playerDTO.category(), playerDTO.role(), playerDTO.arenas());

        form = new Playerform("String", "email@string.com","string", LocalDate.now(), Gender.female, 1, roles, arenaId);
    }

    @Test
    public void getById() {

        //Arrange

        when(playerRepository.findById(anyLong())).thenReturn(Optional.of(player));
        //act
        Optional<Player> search = playerService.getOne(1L);
        // assert
        assertTrue(search.isPresent());
        assertEquals(player, search.get());
    }

    @Test
    void getById_when_not_found(){
        when(playerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Player> search = playerService.getOne(1L);

        assertFalse(search.isPresent());
    }

    @Test
    void create_when_ok(){

        when(arenaRepository.findAllById(any())).thenReturn(List.of(arena1));
        when(playerRepository.save(any(Player.class))).thenReturn(player);
        playerService.create(form);
        verify(playerRepository, times(1)).save(any(Player.class));


    }
    @Test
    void when_create_null(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> playerService.create(null) );

        String exepctedMessage = "Le formulaire ne peut pas être vide";

        String actualMessage = exception.getMessage();

        assertEquals(exepctedMessage, actualMessage);
    }

    @Test
    void register_when_ok(){
        when(playerService.getOne(anyLong())).thenReturn(Optional.of(player));

        when(arenaRepository.findAllById(any())).thenReturn(List.of(arena1));

        playerService.register(1L, 1L);

        verify(playerRepository, times(1)).save(any(Player.class));
    }



    @Test
    void delete_when_ok (){
        // quand playerService.getone(nimportequelid) alors retourne un optional de player
        when(playerService.getOne(anyLong())).thenReturn(Optional.of(player));
        // quand arenarepository.findbyid(estegalà1L) alors retourne un optional de Arena
        when(arenaRepository.findById(eq(1L))).thenReturn(Optional.of(new Arena()));

        playerService.unregister(1L, 1L);

        verify(playerRepository, times(1)).findById(1L);
        verify(arenaRepository, times(1)).findById(1L);
    }
}
