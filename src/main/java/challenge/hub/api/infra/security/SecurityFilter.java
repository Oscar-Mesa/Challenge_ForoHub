package challenge.hub.api.infra.security;

import challenge.hub.api.repository.IUserRepository;
import challenge.hub.api.domain.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private IUserRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.replace("Bearer ", "");
                logger.info("Extracted token: " + token);
                String subject = tokenService.getSubject(token);

                if (subject != null) {
                    User user = (User) usuarioRepository.findUserDetailsByName(subject);
                    if (user != null) {
                        logger.info("User found: " + subject);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        logger.error("User not found: " + subject);
                        throw new IllegalArgumentException("User not found: " + subject);
                    }
                } else {
                    logger.error("Token subject is null");
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Auth error: " + e.getMessage(), e);
        }

        filterChain.doFilter(request, response);
    }
}