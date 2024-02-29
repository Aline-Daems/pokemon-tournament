package be.techonbel.pokemontournament.pl.controllers;

import be.techonbel.pokemontournament.bl.services.PlayerService;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.pl.dtos.ArenaDTO;
import be.techonbel.pokemontournament.pl.dtos.AuthDTO;
import be.techonbel.pokemontournament.pl.forms.LoginForm;
import be.techonbel.pokemontournament.pl.forms.Playerform;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@CrossOrigin
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/create")
    public void createPlayer(@RequestBody Playerform form){
        playerService.create(form);
    }
    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public AuthDTO login(@RequestBody LoginForm form){
        return playerService.login(form);
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/register/{arenaId}")
    public void register(Authentication authentication, @PathVariable Long arenaId ){

        playerService.register(authentication.getPrincipal().toString(), arenaId);
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/unregister/{arenaId}")
    public void unregister(Authentication authentication, @PathVariable Long arenaId){
        playerService.unregister(authentication.getPrincipal().toString(),arenaId);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/arenas/")
    public ResponseEntity< List<ArenaDTO>> searchArena(Authentication authentication){


        List<Arena> arenas = playerService.allArenas(authentication.getPrincipal().toString());

        List<ArenaDTO>dtos = arenas.stream().map(ArenaDTO::fromEntity).toList();

        return ResponseEntity.ok(dtos);
    }

}
