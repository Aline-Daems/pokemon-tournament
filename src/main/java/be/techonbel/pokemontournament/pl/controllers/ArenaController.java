package be.techonbel.pokemontournament.pl.controllers;

import be.techonbel.pokemontournament.bl.services.ArenaService;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.pl.dtos.ArenaDTO;
import be.techonbel.pokemontournament.pl.dtos.ArenaGetOneDTO;
import be.techonbel.pokemontournament.pl.forms.ArenaForm;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arena")
@CrossOrigin
public class ArenaController {

    private ArenaService arenaService;

    public ArenaController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public void createArena(@RequestBody ArenaForm form){
        arenaService.create(form);
    }
    @PreAuthorize("hasRole('champion')")
    @DeleteMapping("/delete/{id}")
    public void deleteArena(@PathVariable Long id){
        arenaService.delete(id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getAll")
    public ResponseEntity<List<ArenaDTO>> getAll(){
        List<Arena> arenas = arenaService.getAll();

        List<ArenaDTO> dtos = arenas.stream().map(ArenaDTO::fromEntity).toList();

        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public  ResponseEntity<ArenaDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ArenaDTO.fromEntity(arenaService.getOne(id).orElseThrow(()-> new EntityNotFoundException("Id non trouvé"))));

    }
    //@PreAuthorize("hasRole('champion')")
    @PreAuthorize("isAnonymous()")
    @PutMapping("/start/{id}")
    public void start(@PathVariable Long id) {
        arenaService.start(id);

    }

}
