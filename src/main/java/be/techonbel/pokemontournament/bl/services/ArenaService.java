package be.techonbel.pokemontournament.bl.services;

import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.pl.dtos.ArenaGetOneDTO;
import be.techonbel.pokemontournament.pl.forms.ArenaForm;

import java.util.List;
import java.util.Optional;

public interface ArenaService {
    void create(ArenaForm form);

    Optional<Arena> getOne(Long id);
    void delete(Long id);
    List<Arena> getAll();
    void start(Long id);




}
