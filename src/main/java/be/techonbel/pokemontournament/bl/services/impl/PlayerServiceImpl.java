package be.techonbel.pokemontournament.bl.services.impl;

import be.techonbel.pokemontournament.bl.services.PlayerService;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.repositories.PlayerRepository;
import be.techonbel.pokemontournament.pl.forms.Playerform;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void create(Playerform form) {

        if(form == null){
            throw  new IllegalArgumentException("Le formulaire ne peut pas être vide");
        }

        Player player = new Player();
        player.setPseudo(form.pseudo());
        player.setMail(form.mail());
        player.setPassword(form.password());
        player.setBirthdate(form.birthdate());
        player.setGender(form.gender());
        player.setBadges(form.badges());
        player.setRole(form.role());

        playerRepository.save(player);


    }
}
