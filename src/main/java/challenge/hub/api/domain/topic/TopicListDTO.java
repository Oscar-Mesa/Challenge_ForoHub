package challenge.hub.api.domain.topic;

import java.time.LocalDateTime;

public record TopicListDTO(
        Long id,
        String title,
        String message,
        LocalDateTime creationDate,
        Boolean status,
        String author,
        String course
) {
    public TopicListDTO(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getStatus(),
                topic.getAuthor().getName(),
                topic.getCourse().getName());
    }
}
