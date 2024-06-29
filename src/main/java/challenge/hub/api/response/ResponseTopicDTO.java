package challenge.hub.api.response;

import java.time.LocalDateTime;


public record ResponseTopicDTO(Long id, String title, String message, LocalDateTime creationDate,
                               String author, String course) {
}
