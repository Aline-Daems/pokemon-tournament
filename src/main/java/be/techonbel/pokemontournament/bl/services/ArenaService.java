package be.techonbel.pokemontournament.bl.services;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.pl.dtos.ArenaGetOneDTO;
import be.techonbel.pokemontournament.pl.forms.ArenaForm;

import java.util.List;

public interface ArenaService {
    void create(ArenaForm form);

    Arena getOne(Long id);
    void delete(Long id);
    List<Arena> getAll();

//    ToRegister getRegister(Long id);
}
