package challenge.hub.api.domain.course;

import challenge.hub.api.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.usertype.CompositeUserType;

import java.util.List;

@Table(name = "tbl_course")
@Entity(name = "Course")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private List<Topic> topics;

    public Course(CourseDTO courseDTO){
        this.name = courseDTO.getName();
        this.category = courseDTO.getCategory();
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
