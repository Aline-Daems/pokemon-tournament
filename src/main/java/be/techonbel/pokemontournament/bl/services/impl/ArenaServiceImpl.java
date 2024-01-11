package be.techonbel.pokemontournament.bl.services.impl;

import be.techonbel.pokemontournament.bl.services.ArenaService;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.repositories.ArenaRepository;
import be.techonbel.pokemontournament.pl.forms.ArenaForm;
import org.springframework.stereotype.Service;

@Service
public class ArenaServiceImpl implements ArenaService {

    private final ArenaRepository arenaRepository;

    public ArenaServiceImpl(ArenaRepository arenaRepository) {
        this.arenaRepository = arenaRepository;
    }

    @Override
    public void create(ArenaForm form) {

        if(form == null){
            throw  new IllegalArgumentException("Le formulaire ne peut pas être vide");
        }


        Arena arena = new Arena();

        arena.setCity(form.city());
        arena.setNbMinPlayer(form.nbMinPlayer());
        arena.setNbMaxPlayer(form.nbMaxPlayer());
        arena.setType(form.type());
        arena.setStatus(form.status());
        arena.setRound(form.round());
        arena.setWomenOnly(form.womenOnly());
        arena.setClosingDate(form.closingDate());
        arena.setCreationDate(form.creationDate().now());
        arena.setUpdateDate(form.updateDate());

        arenaRepository.save(arena);

    }
}
