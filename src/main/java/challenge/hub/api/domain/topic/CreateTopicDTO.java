package challenge.hub.api.domain.topic;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicDTO(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotNull
        Boolean status,
        @NotBlank
        String author,
        @NotBlank
        String course
        ) {
}
