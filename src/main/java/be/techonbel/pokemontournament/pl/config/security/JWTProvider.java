package be.techonbel.pokemontournament.pl.config.security;

import be.techonbel.pokemontournament.dal.models.entities.Player;
import be.techonbel.pokemontournament.dal.models.entities.enums.Roles;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class JWTProvider {

    private static final String JWT_SECRET ="vQ6c35cj455yAcpy6hxABG6w5k8t98SzG338ALeUT7Kg77bf887r8f98A3rPjMQZEbB2pyYA2Mk8ebDLLL2SWM8Wit";
    private static final long EXPIRES_AT = 900;
    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;

    public JWTProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public String generateToken(String username, String email , List<Roles> roles) {
        return TOKEN_PREFIX + JWT.create()
                .withSubject(username)
                .withSubject(email)
                .withClaim("roles", roles.stream().map(Enum::toString).toList())
                .withExpiresAt(Instant.now().plusMillis(EXPIRES_AT))
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public String extractToken(HttpServletRequest request){
        String header = request.getHeader(AUTH_HEADER);

        if(header == null || !header.startsWith(TOKEN_PREFIX))
            return null;
        return header.replaceFirst(TOKEN_PREFIX, "");
    }

    public boolean validateToken(String token){

        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JWT_SECRET))
                    .acceptExpiresAt(EXPIRES_AT)
                    .withClaimPresence("sub")
                    .withClaimPresence("roles")
                    .build().verify(token);

            String username = jwt.getSubject();

            Player player = (Player) userDetailsService.loadUserByUsername(username);

            if(!player.isEnabled()){
                return false;
            }

            List<Roles> tokenRoles = jwt.getClaim("roles").asList(Roles.class);

            return player.getRole().containsAll(tokenRoles);

        } catch (JWTVerificationException | UsernameNotFoundException ex) {
            return  false;
        }
    }

    public Authentication createAuthentification (String token){
        DecodedJWT jwt = JWT.decode(token);

        String username = jwt.getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
        );
    }
}
