package challenge.hub.api.domain.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateTopicDTO(
         String title,
         String message,
         Boolean status

) {
}
