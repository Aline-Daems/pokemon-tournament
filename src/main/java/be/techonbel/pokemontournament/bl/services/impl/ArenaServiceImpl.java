package be.techonbel.pokemontournament.bl.services.impl;

import be.techonbel.pokemontournament.bl.services.ArenaService;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Match;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import be.techonbel.pokemontournament.dal.repositories.ArenaRepository;
import be.techonbel.pokemontournament.dal.repositories.MatchRepository;
import be.techonbel.pokemontournament.dal.repositories.PlayerRepository;
import be.techonbel.pokemontournament.pl.forms.ArenaForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ArenaServiceImpl implements ArenaService {

    private final ArenaRepository arenaRepository;
    private final PlayerRepository playerRepository;

    private final MatchRepository matchRepository;



    public ArenaServiceImpl(ArenaRepository arenaRepository, PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.arenaRepository = arenaRepository;

        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public void create(ArenaForm form) {

        if(form == null){
            throw  new IllegalArgumentException("Le formulaire ne peut pas être vide");
        }

        Arena arena = new Arena();

        arena.setCity(form.city());
        arena.setCategory(form.category());
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


    @Override
    public void start(Long id) {

        Arena arena = arenaRepository.getOne(id);
        
        LocalDate date = LocalDate.now();

        if(arena.getNbMinPlayer() > arena.getNbPlayer() || arena.getClosingDate().isBefore(date) || arena.getStatus() == Status.inProgress || arena.getStatus() == Status.finished){
            throw new IllegalArgumentException("Yann pas content!");

        }else {
            arena.setStatus(Status.inProgress);
            arena.setRound(1);
            arena.setUpdateDate(date);

            arenaRepository.save(arena);

            List<Player> players = arena.getPlayers();

            for (int i = 0; i< players.size(); i++) {
                for (int j = i+1; j<players.size(); j++){
                    Match match1 = new Match();
                    match1.setPlayer1(players.get(i));
                    match1.setPlayer2(players.get(j));
                    match1.setArena(arena);

                    Match match2 = new Match();
                    match2.setPlayer1(players.get(j));
                    match2.setPlayer2(players.get(i));
                    match2.setArena(arena);

                    List<String> pokemons = new ArrayList<>();

                    pokemons.add("Pikachu");
                    pokemons.add("Bulbizarre");
                    pokemons.add("Carapuce");
                    pokemons.add("Salamèche");
                    pokemons.add("Rondoudou");
                    pokemons.add("Miaouss");
                    pokemons.add("Psykokwak");
                    pokemons.add("Nosferapti");
                    pokemons.add("Évoli");
                    pokemons.add("Ronflex");
                    pokemons.add("Feunard");
                    pokemons.add("Léviator");
                    pokemons.add("Abra");
                    pokemons.add("Machoc");
                    pokemons.add("Fantominus");
                    pokemons.add("Onix");
                    pokemons.add("Soporifik");
                    pokemons.add("Osselait");
                    pokemons.add("Kicklee");
                    pokemons.add("Tygnon");

                    Random random = new Random();

                    int index = random.nextInt(pokemons.size());
                    String pokemonChoisi1 = pokemons.get(index);

                    match1.setPokemonPlayer(pokemonChoisi1);

                    int index2 = random.nextInt(pokemons.size());
                    String pokemonChoisi2 = pokemons.get(index2);
                    match1.setPokemonChampion(pokemonChoisi2);


                    int index3 = random.nextInt(pokemons.size());
                    String pokemonChoisi3 = pokemons.get(index3);

                    match2.setPokemonPlayer(pokemonChoisi3);

                    int index4 = random.nextInt(pokemons.size());
                    String pokemonChoisi4 = pokemons.get(index4);
                    match2.setPokemonChampion(pokemonChoisi4);

                    List<String> resultats = new ArrayList<>();

                    resultats.add("Victoire du Champion" );
                    resultats.add("Victoire du challenger");
                    resultats.add("Pas encore joué");
                    resultats.add("égalité");

                    int indexResult = random.nextInt(resultats.size());
                    String finalResult1 =  resultats.get(indexResult);

                    int indexResult2 = random.nextInt(resultats.size());
                    String finalResult2 =  resultats.get(indexResult2);
                    match1.setResult(finalResult1);
                    match2.setResult(finalResult2);

                    matchRepository.save(match1);
                    matchRepository.save(match2);

                }

            }
        }

    }
}
