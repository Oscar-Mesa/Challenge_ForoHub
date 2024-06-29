package challenge.hub.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@OpenAPIDefinition(
        info = @Info(
                title = "ONE FINAL",
                version = "0.0.1",
                description = "Final project ONE"
        ),
        security = @SecurityRequirement(name = "bearer-key")
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearer-key",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER
        )
})
public class swagger implements WebMvcConfigurer {
}
