package be.techonbel.pokemontournament.bl.services.impl;

import be.techonbel.pokemontournament.bl.services.PlayerService;
import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.repositories.PlayerRepository;
import be.techonbel.pokemontournament.pl.config.security.JWTProvider;
import be.techonbel.pokemontournament.pl.dtos.AuthDTO;
import be.techonbel.pokemontournament.pl.forms.LoginForm;
import be.techonbel.pokemontournament.pl.forms.Playerform;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final AuthenticationManager authenticationManager;

    private final JWTProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public PlayerServiceImpl(PlayerRepository playerRepository, AuthenticationManager authenticationManager, JWTProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public AuthDTO login(LoginForm form) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getPseudo(), form.getPassword()));
        Player player = playerRepository.findByPseudo(form.getPseudo()).get();

        String token = jwtProvider.generateToken(player.getUsername(), List.copyOf(player.getRole()));
        AuthDTO authDTO = AuthDTO.create(token, player.getPseudo(), player.getRole() );
        return authDTO;

    }
}
