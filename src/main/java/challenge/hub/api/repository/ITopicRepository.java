package challenge.hub.api.repository;

import challenge.hub.api.domain.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITopicRepository extends JpaRepository<Topic,Long> {
    Optional<Topic> findByTitleAndMessage(String title, String message);
}
