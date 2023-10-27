package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.StudentDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SubjectDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.StudentBean;
import jakarta.ejb.EJB;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Path("students") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class StudentService {
    @EJB
    private StudentBean studentBean;

    // Converts an entity Student to a DTO Student class

    private StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getUsername(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourseCode(), // Adicionar courseCode
                student.getCourseName()  // Adicionar courseName
        );
    }

    // converts an entire list of entities into a list of DTOs
    private List<StudentDTO> toDTOs(List<Student> students) {
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<StudentDTO> getAllStudents() {
        return toDTOs(studentBean.getAll());
    }

    @POST
    @Path("/")
    public Response create(StudentDTO studentDTO)
            throws MyEntityExistsException, MyEntityNotFoundException {
        studentBean.create(
                studentDTO.getUsername(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode()
        );
        Student student = studentBean.find(studentDTO.getUsername());
        return Response.status(Response.Status.CREATED).entity(toDTO(student)).build();
    }

    @GET
    @Path("{username}") // means: the relative url path is “/api/students/{username}”
    public Response getStudentDetails(@PathParam("username") String username) {
        Student student = studentBean.find(username);
        if (student != null) {
            return Response.ok(toDTO(student)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    public SubjectDTO toDTO(Subject subject) {
        return new SubjectDTO(
                subject.getCode(),
                subject.getName(),
                subject.getCourse().getCode(),
                subject.getCourse().getName(),
                subject.getCourseYear(),
                subject.getScholarYear()
        );
    }

    public List<SubjectDTO> subjectsToDTOs(List<Subject> subjects) {
        return subjects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("{username}/subjects")
    public Response getStudentSubjects(@PathParam("username") String username) {
        var student = studentBean.getStudentWithSubjects(username);
        if (student != null) {
            var dtos = subjectsToDTOs(student.getSubjects());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_STUDENT")
                .build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteStudent(@PathParam("username") String username) {
        studentBean.remove(username);
        return Response.ok().build();
    }

    @PUT
    @Path("{username}")
    public Response updateStudent(@PathParam("username") String username, StudentDTO studentDTO) {
        var student = studentBean.find(username);
        if (student == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        studentBean.update(username, studentDTO.getPassword(), studentDTO.getName(), studentDTO.getEmail(), studentDTO.getCourseCode());
        return Response.ok().build();
    }
}
