package be.techonbel.pokemontournament.pl.controllers;

import be.techonbel.pokemontournament.bl.services.ArenaService;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.pl.dtos.ArenaDTO;
import be.techonbel.pokemontournament.pl.dtos.ArenaGetOneDTO;
import be.techonbel.pokemontournament.pl.forms.ArenaForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arena")
public class ArenaController {

    private ArenaService arenaService;

    public ArenaController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @PostMapping("/create")
    public void createArena(@RequestBody ArenaForm form){
        arenaService.create(form);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteArena(@PathVariable Long id){
        arenaService.delete(id);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ArenaDTO>> getAll(){
        List<Arena> arenas = arenaService.getAll();

        List<ArenaDTO> dtos = arenas.stream().map(ArenaDTO::fromEntity).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<ArenaDTO> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(ArenaDTO.fromEntity(arenaService.getOne(id)));


    }

    @PutMapping("/start/{id}")
    public void start(@PathVariable Long id) {
        arenaService.start(id);
    }

}
