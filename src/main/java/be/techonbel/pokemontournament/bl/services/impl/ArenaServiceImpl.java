package be.techonbel.pokemontournament.bl.services.impl;

import be.techonbel.pokemontournament.bl.services.ArenaService;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import be.techonbel.pokemontournament.dal.repositories.ArenaRepository;
import be.techonbel.pokemontournament.dal.repositories.PlayerRepository;
import be.techonbel.pokemontournament.pl.dtos.ArenaDTO;
import be.techonbel.pokemontournament.pl.dtos.ArenaGetOneDTO;
import be.techonbel.pokemontournament.pl.forms.ArenaForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArenaServiceImpl implements ArenaService {

    private final ArenaRepository arenaRepository;
    private final PlayerRepository playerRepository;



    public ArenaServiceImpl(ArenaRepository arenaRepository, PlayerRepository playerRepository) {
        this.arenaRepository = arenaRepository;

        this.playerRepository = playerRepository;
    }

    @Override
    public void create(ArenaForm form) {

        if(form == null){
            throw  new IllegalArgumentException("Le formulaire ne peut pas être vide");
        }

        Arena arena = new Arena();

        arena.setCity(form.city());
        arena.setType(form.type());
        arena.setNbMaxPlayer(form.nbMaxPlayer());
        if (form.nbMinPlayer()>form.nbMaxPlayer())
            throw  new RuntimeException("Yann pas content");
        arena.setNbMinPlayer(form.nbMinPlayer());
        arena.setNbPlayer(form.nbPlayer());
        arena.setStatus(form.status());
        arena.setRound(form.round());
        arena.setBadgeMin(form.badgeMin());
        arena.setBadgeMax(form.badgeMax());
        if(form.womenOnly() == null){
            arena.setWomenOnly(false);
        }else{
            arena.setWomenOnly(true);
        }
        arena.setCreationDate(form.creationDate().now().plusDays(form.nbPlayer()));
        arena.setUpdateDate(form.updateDate().now().plusDays(form.nbPlayer()));
        LocalDate closingDate = arena.getCreationDate();
        arena.setClosingDate(closingDate.plusDays(form.nbPlayer()));

        arena.setPlayers(new ArrayList<>(playerRepository.findAllById((form.playerId()))));

        arenaRepository.save(arena);

    }

    @Override
    public Arena getOne(Long id) {
        Arena arena=  arenaRepository.findByArenaIdWithPlayer(id).orElseThrow(EntityNotFoundException::new);

        return  arena;

    }

    @Override
    public void delete(Long id) {
       Arena arena =  arenaRepository.getOne(id);
       if(arena.getStatus() == Status.inProgress)
           throw new IllegalArgumentException("Yann pas content!");

            arenaRepository.deleteById(id);

    }

    @Override
    public List<Arena> getAll() {
        return arenaRepository.findTop10AllByOrderByUpdateDateDesc();
    }



}
