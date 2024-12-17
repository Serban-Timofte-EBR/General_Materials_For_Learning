package eu.ase.ro.spring.mvc.service;

import eu.ase.ro.spring.mvc.model.Course;
import eu.ase.ro.spring.mvc.model.Student;
import eu.ase.ro.spring.mvc.reponse.CourseResponse;
import eu.ase.ro.spring.mvc.reponse.StudentResponse;
import eu.ase.ro.spring.mvc.repository.CourseRepository;
import eu.ase.ro.spring.mvc.request.CourseRequest;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @PostConstruct
    private void init() {
        List<Course> courses = List.of(
                new Course("MPAI - Spring MVC", "Mr T", "totul despre modelul MVC implementat cu framekwork-ul Spring",
                        List.of(new Student("Popescu Ion", 23))),
                new Course("Java fundamentals", "Mr T", "hai sa invatam Java", List.of(new Student("Ionescu Maria", 22),
                        new Student("Voicu Daniel", 22)))
        );
        courseRepository.saveAll(courses);
    }

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void save(CourseRequest request) {
        Course course = new Course(
                request.getName(),
                request.getTrainer(),
                request.getDescription()
        );
        courseRepository.save(course);
    }

    public List<CourseResponse> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::toCourseResponse)
                .collect(Collectors.toList());
    }

    private CourseResponse toCourseResponse(Course course) {
        int id = course.getId();
        String name = course.getName();
        String trainer = course.getTrainer();
        String description = course.getDescription();
        List<StudentResponse> students =
                course.getStudents() == null
                ? List.of()
                        : course.getStudents()
                        .stream()
                        .map(this::toStudentResponse)
                        .toList();

        return new CourseResponse(
                id,
                name,
                trainer,
                description,
                students
        );
    }

    private StudentResponse toStudentResponse(Student student) {
        int id = student.getId();
        String name = student.getName();
        int age = student.getAge();

        return new StudentResponse(
                id,
                name,
                age
        );
    }

    public CourseResponse findById(int id) {
        return courseRepository.findById(id)
                .map(this::toCourseResponse)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public void updateById(Integer courseId, CourseRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setName(request.getName());
        course.setTrainer(request.getTrainer());
        course.setDescription(request.getDescription());
        courseRepository.save(course);
    }

    public void removeById(Integer courseId) {
        courseRepository.deleteById(courseId);
    }

    public void unenrollStudent(Integer courseId, Integer studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Student> students = course.getStudents()
                .stream()
                .filter(student -> student.getId() != studentId)
                .collect(Collectors.toList());

        course.setStudents(students);
        courseRepository.save(course);
    }
}
