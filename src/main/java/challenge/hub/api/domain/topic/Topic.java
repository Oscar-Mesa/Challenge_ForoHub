package challenge.hub.api.domain.topic;

import challenge.hub.api.domain.answer.Answer;
import challenge.hub.api.domain.course.Course;
import challenge.hub.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.Date;
import java.util.List;

@Table(name="tbl_topic")
@Entity(name = "Topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    private LocalDateTime creationDate;
    private Boolean status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Answer> answers;

    public Topic(CreateTopicDTO createTopicDTO){
        this.title = createTopicDTO.title();
        this.message = createTopicDTO.message();
        LocalDateTime cdate = LocalDateTime.now();
        this.creationDate = cdate;
        this.status = createTopicDTO.status();
        this.author = getAuthor();
        this.course = getCourse();
    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getStatus(){
        return status;
    }

    public boolean isStatus() {
        return status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }


    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Answer> getAnswers() {
        return answers;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}



