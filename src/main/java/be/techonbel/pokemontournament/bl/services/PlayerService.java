package be.techonbel.pokemontournament.bl.services;

import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.pl.dtos.AuthDTO;
import be.techonbel.pokemontournament.pl.forms.LoginForm;
import be.techonbel.pokemontournament.pl.forms.Playerform;

import java.util.Optional;

public interface PlayerService {
    void create(Playerform form);
    AuthDTO login(LoginForm form);

    Optional<Player> getOne(Long id);

    void register(Long id, Long ArenaId);

    void unregister(Long id, Long ArenaId);
}
