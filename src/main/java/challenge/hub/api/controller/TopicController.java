package challenge.hub.api.controller;


import challenge.hub.api.domain.course.Course;
import challenge.hub.api.repository.ICourseRepository;
import challenge.hub.api.domain.topic.CreateTopicDTO;
import challenge.hub.api.domain.topic.Topic;
import challenge.hub.api.domain.topic.TopicListDTO;
import challenge.hub.api.domain.topic.UpdateTopicDTO;
import challenge.hub.api.repository.IUserRepository;
import challenge.hub.api.domain.user.User;
import challenge.hub.api.response.ResponseTopicDTO;
import challenge.hub.api.repository.ITopicRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import io.swagger.v3.oas.annotations.Operation;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topic")
@SecurityRequirement(name ="bearer-key")
@Tag(name = "Topic", description = "CRUD Topic")
public class TopicController {

    private final ITopicRepository iTopicRepository;
    private final ICourseRepository iCourseRepository;
    private final IUserRepository iUserRepository;

    public TopicController(ITopicRepository itopicRepository, ICourseRepository iCourseRepository,
    IUserRepository iUserRepository) {
        this.iTopicRepository = itopicRepository;
        this.iCourseRepository = iCourseRepository;
        this.iUserRepository = iUserRepository;

    }
    
    public Topic validationPostTopic(CreateTopicDTO createTopicDTO ,String username, String coursename){
        User user = iUserRepository.findByName(username).orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        Course course = iCourseRepository.findByName(coursename).orElseThrow(()->
                new IllegalArgumentException("Course not found"));

        Topic topic = new Topic(createTopicDTO);
        topic.setAuthor(user);
        topic.setCourse(course);
        topic.setCreationDate(LocalDateTime.now());

        return topic;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Create Topic", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<?> PostTopic(@RequestBody @Valid CreateTopicDTO createTopicDTO, UriComponentsBuilder uriComponentsBuilder){

        Optional<Topic> existingTopic = iTopicRepository.findByTitleAndMessage(createTopicDTO.title(), createTopicDTO.message());
        if (existingTopic.isPresent()) {
            String errorMessage = "This topic already exists";
            return ResponseEntity.badRequest().body(errorMessage);
        }

        Topic topic = validationPostTopic( createTopicDTO,createTopicDTO.author(),createTopicDTO.course());
        iTopicRepository.save(topic);

        ResponseTopicDTO responseTopicDTO =
                new ResponseTopicDTO(
                        topic.getId(),
                        topic.getTitle(),
                        topic.getMessage(),
                        topic.getCreationDate(),
                        topic.getAuthor().getName(),
                        topic.getCourse().getName()
                        );

        URI url = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(url).body(responseTopicDTO);


    }

    @GetMapping
    @Operation(summary = "List all Topics", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<TopicListDTO>> getAllTopics() {
        List<Topic> topics = iTopicRepository.findAll();
        List<TopicListDTO> topicListDTOs = topics.stream()
                .map(TopicListDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topicListDTOs);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update Topic", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<ResponseTopicDTO> updateTopic(@PathVariable Long id, @RequestBody @Valid UpdateTopicDTO updateTopicDTO) {
        Optional<Topic> optionalTopic = iTopicRepository.findById(id);
        if (optionalTopic.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topic topic = optionalTopic.get();
        if (updateTopicDTO.title() != null && !updateTopicDTO.title().equals(topic.getTitle())) {
            topic.setTitle(updateTopicDTO.title());
        }
        if (updateTopicDTO.message() != null && !updateTopicDTO.message().equals(topic.getMessage())) {
            topic.setMessage(updateTopicDTO.message());
        }
        if (updateTopicDTO.status() != null && !updateTopicDTO.status().equals(topic.getStatus())) {
            topic.setStatus(updateTopicDTO.status());
        }

        iTopicRepository.save(topic);


        ResponseTopicDTO responseTopicDTO = new ResponseTopicDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getAuthor().getName(),
                topic.getCourse().getName()
        );

        return ResponseEntity.ok(responseTopicDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete Topic", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        Optional<Topic> optionalTopic = iTopicRepository.findById(id);
        if (optionalTopic.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        iTopicRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Topic data", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<ResponseTopicDTO>GetTopicById(@PathVariable Long id){
        Optional<Topic> topic = iTopicRepository.findById(id);

        ResponseTopicDTO responseTopicDTO = new ResponseTopicDTO(
                topic.get().getId(),
                topic.get().getTitle(),
                topic.get().getMessage(),
                topic.get().getCreationDate(),
                topic.get().getAuthor().getName(),
                topic.get().getCourse().getName()
        );
        return ResponseEntity.ok(responseTopicDTO);
    }


}
