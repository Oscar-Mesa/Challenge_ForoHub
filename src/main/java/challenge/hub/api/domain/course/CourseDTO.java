package challenge.hub.api.domain.course;

public record CourseDTO(String name, String category) {
    public String getName(){ return this.name; }
    public String getCategory(){ return this.category; }
}
