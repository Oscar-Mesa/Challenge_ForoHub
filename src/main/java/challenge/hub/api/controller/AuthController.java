package challenge.hub.api.controller;

import challenge.hub.api.domain.user.AuthDTO;
import challenge.hub.api.domain.user.User;
import challenge.hub.api.infra.security.DataJWTToken;
import challenge.hub.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {
    final AuthenticationManager authenticationManager;

    final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {

        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DataJWTToken> authUse(@RequestBody @Valid AuthDTO authDTO) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(authDTO.name(),
                authDTO.password());
        var userAuth = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.GenerateToken((User) userAuth.getPrincipal());
        return ResponseEntity.ok(new DataJWTToken(JWTtoken));
    }
}
