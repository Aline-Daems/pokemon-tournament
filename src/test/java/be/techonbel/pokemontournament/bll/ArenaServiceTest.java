package be.techonbel.pokemontournament.bll;

import be.techonbel.pokemontournament.bl.services.impl.ArenaServiceImpl;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import be.techonbel.pokemontournament.dal.repositories.ArenaRepository;
import be.techonbel.pokemontournament.pl.dtos.ArenaDTO;
import be.techonbel.pokemontournament.pl.dtos.ArenaDTOALL;
import be.techonbel.pokemontournament.pl.dtos.PlayerDTO;
import be.techonbel.pokemontournament.pl.dtos.PlayerDTOAll;
import be.techonbel.pokemontournament.pl.forms.ArenaForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArenaServiceTest {

    @InjectMocks
    private ArenaServiceImpl arenaService;
    @Mock
    ArenaRepository arenaRepository;
    private Arena arena;
    private ArenaDTOALL arenaDTO;
    private PlayerDTO playerDTO;
    private Player player;

    private ArenaForm form;

    @BeforeEach
    void setUp(){

        playerDTO = new PlayerDTO( 1L,"String850");

        player = new Player(playerDTO.pseudo());

        List<PlayerDTO> players = Arrays.asList(playerDTO);

        arenaDTO = new ArenaDTOALL(1L, "Arlon", 2, 4, 0, Category.Junior, Status.pending, 0, 1, 3 , false, LocalDate.now(), LocalDate.now(), LocalDate.now(), players );
        arena = new Arena(arenaDTO.arenaId(), arenaDTO.city(), arenaDTO.nbMinPlayer(), arenaDTO.nbMaxPlayer(), arenaDTO.nbPlayer(),arenaDTO.category(), arenaDTO.status(), arenaDTO.round(), arenaDTO.badgeMin(), arenaDTO.badgeMax(), arenaDTO.womenOnly() ,arenaDTO.closingDate(), arenaDTO.crationDate(), arenaDTO.updateDate() );
        form = new ArenaForm("Arlon", 2, 4, 0, Category.Junior, Status.pending, 0, 1, 3 , false, LocalDate.now(), LocalDate.now());

    }

    @Test
    void getById(){

        when(arenaRepository.findByArenaIdWithPlayer(anyLong())).thenReturn(Optional.of(arena));

        Optional<Arena> search = arenaService.getOne(1L);

        assertTrue(search.isPresent());

        assertEquals(arena, search.get());
    }


    @Test
    void getId_when_not_found(){
        when(arenaRepository.findByArenaIdWithPlayer(anyLong())).thenReturn(Optional.empty());

        Optional<Arena> search = arenaService.getOne(1L);

        assertFalse(search.isPresent());

    }
    @Test
    void when_create_ok(){
        when(arenaRepository.save(any(Arena.class))).thenReturn(arena);

        arenaService.create(form);

        verify(arenaRepository, times(1)).save(any(Arena.class));
    }

    @Test
    void when_create_null(){

        Exception exception = assertThrows(IllegalArgumentException.class, () -> arenaService.create(null));

        String exepctedMessage = "Le formulaire ne peut pas être vide";

        String actualMessage = exception.getMessage();

        assertEquals(exepctedMessage, actualMessage);

    }

    @Test
    void when_delete_ok(){
        when(arenaRepository.findById(anyLong())).thenReturn(Optional.of(arena));
        arenaService.delete(1L);
        verify(arenaRepository, times(1)).findById(1L);

    }
    @Test
    void getAll(){

        List<Arena> arenaList = Arrays.asList(arena, arena, arena);

        when(arenaRepository.findTop10AllByOrderByUpdateDateDesc()).thenReturn(arenaList);

        List<Arena> result = arenaService.getAll();

        assertEquals(arenaList, result);
        verify(arenaRepository, times(1)).findTop10AllByOrderByUpdateDateDesc();
    }



}
