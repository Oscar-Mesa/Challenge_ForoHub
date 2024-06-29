package challenge.hub.api.repository;

import challenge.hub.api.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
}
