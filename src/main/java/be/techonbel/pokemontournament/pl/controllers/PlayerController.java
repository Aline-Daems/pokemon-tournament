package be.techonbel.pokemontournament.pl.controllers;

import be.techonbel.pokemontournament.bl.services.PlayerService;
import be.techonbel.pokemontournament.pl.dtos.AuthDTO;
import be.techonbel.pokemontournament.pl.forms.LoginForm;
import be.techonbel.pokemontournament.pl.forms.Playerform;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    //@PreAuthorize("isAnonymous()")
    @PostMapping("/create")
    public void createPlayer(@RequestBody Playerform form){
        playerService.create(form);
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestBody LoginForm form){
        return playerService.login(form);
    }

}
