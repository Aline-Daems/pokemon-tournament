package be.techonbel.pokemontournament.bl.services.impl;

import be.techonbel.pokemontournament.bl.services.PlayerService;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.pl.forms.Playerform;

public class PlayerServiceImpl implements PlayerService {

    private final PlayerService playerService;

    public PlayerServiceImpl(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Override
    public void create(Playerform form) {

        if(form == null){
            throw  new IllegalArgumentException("Le formulaire ne peut pas être vide");
        }

        Player player = new Player();
        player.setPseudo(form.pseudo());
        player.setMail(form.mail());

    }
}
