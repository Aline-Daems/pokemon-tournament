package be.techonbel.pokemontournament.bl.services;

import be.techonbel.pokemontournament.pl.dtos.AuthDTO;
import be.techonbel.pokemontournament.pl.forms.LoginForm;
import be.techonbel.pokemontournament.pl.forms.Playerform;

public interface PlayerService {
    void create(Playerform form);

    AuthDTO login(LoginForm form);
}
