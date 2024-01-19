package be.techonbel.pokemontournament.pl.controllers;

import be.techonbel.pokemontournament.bl.services.PlayerService;
import be.techonbel.pokemontournament.pl.dtos.AuthDTO;
import be.techonbel.pokemontournament.pl.forms.LoginForm;
import be.techonbel.pokemontournament.pl.forms.Playerform;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PreAuthorize("hasRole('champion')")
    @PostMapping("/create")
    public void createPlayer(@RequestBody Playerform form){
        playerService.create(form);
    }
    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public AuthDTO login(@RequestBody LoginForm form){
        return playerService.login(form);
    }

    @PutMapping("/register/{id}/{arenaId}")
    public void register(@PathVariable Long id, Long arenaId ){

         playerService.register(id, arenaId);
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/unregister/{id}/{arenaId}")
    public void unregister(@PathVariable Long id, Long arenaId){
        playerService.unregister(id,arenaId);

    }

}
