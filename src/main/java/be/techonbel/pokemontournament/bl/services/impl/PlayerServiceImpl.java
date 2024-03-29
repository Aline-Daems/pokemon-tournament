package be.techonbel.pokemontournament.bl.services.impl;

import be.techonbel.pokemontournament.bl.services.PlayerService;
import be.techonbel.pokemontournament.dal.models.entities.Arena;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Category;
import be.techonbel.pokemontournament.dal.models.entities.enums.Gender;
import be.techonbel.pokemontournament.dal.models.entities.enums.Status;
import be.techonbel.pokemontournament.dal.repositories.ArenaRepository;
import be.techonbel.pokemontournament.dal.repositories.PlayerRepository;
import be.techonbel.pokemontournament.pl.config.security.JWTProvider;
import be.techonbel.pokemontournament.pl.dtos.AuthDTO;
import be.techonbel.pokemontournament.pl.forms.LoginForm;
import be.techonbel.pokemontournament.pl.forms.Playerform;
import jakarta.persistence.Cache;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cglib.core.Local;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ArenaRepository arenaRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public PlayerServiceImpl(PlayerRepository playerRepository, ArenaRepository arenaRepository, AuthenticationManager authenticationManager, JWTProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.arenaRepository = arenaRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(Playerform form) {

        if (form == null) {
            throw new IllegalArgumentException("Le formulaire ne peut pas être vide");
        }

        Player player = new Player();
        player.setPseudo(form.pseudo());
        player.setMail(form.mail());
        player.setPassword(passwordEncoder.encode(form.password()));
        player.setBirthdate(form.birthdate());
        player.setGender(form.gender());
        player.setBadges(form.badges());
        player.setRole(form.role());
        List<Arena> playerArenas = arenaRepository.findAllById(form.arenaId());
        LocalDate date = LocalDate.now();
        for (Arena arena : playerArenas) {
            if (arena.getNbPlayer() < arena.getNbMaxPlayer()) {
                arena.incrementNbPlayer();

                if (arena.getBadgeMin() <= player.getBadges()) {
                    if ((player.getGender() == Gender.female && arena.getWomenOnly()) || (player.getGender() == Gender.male && !arena.getWomenOnly())) {
                        if (arena.getStatus() != Status.inProgress && !arena.getClosingDate().isBefore(date)) {
                            LocalDate birthdate = player.getBirthdate();
                            LocalDate endDate = arena.getClosingDate();

                            Period agePeriod = Period.between(birthdate, endDate);

                            int age = agePeriod.getYears();

                            if (age < 18) {
                                player.setCategory(Category.Junior);
                            } else if (age >= 18 && age < 60) {
                                player.setCategory(Category.Senior);
                            } else {
                                player.setCategory(Category.Veteran);
                            }

                            if (player.getCategory() == arena.getCategory()) {

                                player.setArenas(new ArrayList<>(playerArenas));
                                playerRepository.save(player);
                            } else {
                                throw new IllegalArgumentException("Vous n'avez pas l'âge requis pour le tournois");

                            }

                        } else {
                            throw new IllegalArgumentException("Le tournoi est déjà en cours");
                        }
                    } else {
                        throw new IllegalArgumentException("Votre genre ne permets pas de vous inscrire à ce tournoi");
                    }

                } else {
                    throw new IllegalArgumentException("Vous n'avez pas assez de badge pour vous inscrire à ce tournoi");
                }

            } else {
                throw new IllegalArgumentException("Nombre de place au tournoi atteint");
            }
        }
    }

    @Override
    public Optional<Player> getOne(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public AuthDTO login(LoginForm form) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getLogin(), form.getPassword()));
        Player player = playerRepository.findByPseudoOrMail(form.getLogin(), form.getLogin()).get();

        String token = jwtProvider.generateToken(player.getUsername(), player.getMail(), List.copyOf(player.getRole()));
        AuthDTO authDTO = AuthDTO.create(token, player.getPseudo(), player.getRole());
        return authDTO;

    }



    @Override
    public void register(String username, Long arenaId) {
        Player player = playerRepository.findByPseudoOrMail(username, null).orElseThrow();

        List<Arena> playersArenas = arenaRepository.findAllById(Collections.singleton(arenaId));
        LocalDate date = LocalDate.now();

        for (Arena arena : playersArenas) {

            if(arena.getNbPlayer() >= arena.getNbMaxPlayer()){
                throw new IllegalArgumentException("Nombre de places au tournoi atteint");
            }

            arena.incrementNbPlayer();

            if (arena.getBadgeMin() > player.getBadges()){
                throw new IllegalArgumentException("Vous n'avez pas le nombre de badge requis pour ce tournoi");
            } else if (arena.getBadgeMax() < player.getBadges()) {

                throw new IllegalArgumentException("Vous avez trop de badge pour ce tournoi");
            }

            if(arena.getStatus() == Status.inProgress || arena.getClosingDate().isBefore(date)){
                throw new IllegalArgumentException("Le tournoi est déjà en cours ou la date de fermeture de tournoi est déjà dépassée");
            }

            if(player.getGender() == Gender.male && arena.getWomenOnly() ){

                throw  new IllegalArgumentException("Votre genre ne vous permets pas d'accéder à ce tournoi");
            }

            LocalDate birthdate = player.getBirthdate();
            LocalDate endDate = arena.getClosingDate();

            Period agePeriod = Period.between(birthdate, endDate);

            int age = agePeriod.getYears();


            if (age < 18) {
                player.setCategory(Category.Junior);
            } else if (age < 60) {
                player.setCategory(Category.Senior);
            } else {
                player.setCategory(Category.Veteran);
            }

            if(player.getCategory() != arena.getCategory()){
                throw new IllegalArgumentException("Vous n'avez pas l'âge pour ce tournoi");

            }
            if(arena.getPlayers().stream().anyMatch(p -> p.getPlayerId().equals(player.getPlayerId()))){
                throw new IllegalArgumentException("Le joueur est déjà présent dans le tournoi");
            }

            player.setArenas(new ArrayList<>(playersArenas));
            playerRepository.save(player);
        }
    }

    @Override
    public void unregister(String username, Long arenaId) {
        Player player = playerRepository.findByPseudoOrMail(username, null).orElseThrow();
        Arena arena = arenaRepository.findById(arenaId).orElseThrow(() -> new EntityNotFoundException("Id arene non trouvé"));

       try {


           arena.getPlayers().remove(player);
           player.getArenas().remove(arena);


           arenaRepository.save(arena);
           playerRepository.save(player);
       }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("Arene ou joueur non trouvé");
       }


    }

    @Override
    public List<Arena> allArenas(String username) {

       return playerRepository.findArenasByPlayerPseudo(username);
    }
}





