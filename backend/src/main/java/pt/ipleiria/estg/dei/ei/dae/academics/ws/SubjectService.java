package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.StudentDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SubjectDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.SubjectBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;

import java.util.List;

@Path("subjects") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SubjectService {

    @EJB
    private SubjectBean subjectBean;

    private SubjectDTO toDTO(Subject subject) {
        return new SubjectDTO(
                subject.getCode(),
                subject.getName(),
                subject.getCourse().getCode(),
                subject.getCourse().getName(),
                subject.getCourseYear(),
                subject.getScholarYear()
        );
    }

    private StudentDTO toDTO(Student student) {
        return new StudentDTO(
                student.getUsername(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourseCode(),
                student.getCourseName()

        );
    }

    private List<SubjectDTO> toDTOs(List<Subject> subjects) {
        return subjects.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    private List<StudentDTO> studentDTOS(List<Student> students) {
        return students.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    @GET
    @Path("/") // means: the relative url path is “/api/students/”
    public List<SubjectDTO> getAllSubjects() {
        return toDTOs(subjectBean.getAll());
    }

    @GET
    @Path("{code}") // means: the relative url path is “/api/students/”
    public SubjectDTO getSubjectDetails(@PathParam("code") long code) {
        Subject subject = subjectBean.find(code);
        return toDTO(subject);
    }

    @GET
    @Path("course/{courseCode}") // Por exemplo, "/api/subjects/course/1" para obter as disciplinas do curso com código 1.
    public List<SubjectDTO> getSubjectsByCourseCode(@PathParam("courseCode") long courseCode) {
        List<Subject> subjects = subjectBean.getByCourseCode(courseCode); // Suponha que você tenha um método para buscar as disciplinas por código de curso.
        return toDTOs(subjects);
    }

    // Show all students enrolled in a subject;
    @GET
    @Path("{subjectCode}/students") // Por exemplo, "/api/subjects/1001/students" para obter os alunos inscritos na disciplina com código 1001.
    public List<StudentDTO> getStudentsEnrolledInSubject(@PathParam("subjectCode") long subjectCode) {
        List<Student> students = subjectBean.getStudentsEnrolledInSubject(subjectCode);
        return studentDTOS(students);
    }

    @POST
    @Path("/")
    public Response createNewSubject (SubjectDTO subjectDTO){
        subjectBean.create(
                subjectDTO.getCode(),
                subjectDTO.getName(),
                subjectDTO.getCourseCode(),
                subjectDTO.getCourseYear(),
                subjectDTO.getScholarYear()
        );

        Subject newSubject = subjectBean.find(subjectDTO.getCode());
        if(newSubject == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return  Response.status(Response.Status.CREATED).entity(toDTO(newSubject)).build();
    }

    @DELETE
    @Path("{code}")
    public Response deleteSubject(@PathParam("code") long code) {
        try {
            subjectBean.delete(code);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ERROR_DELETING_SUBJECT").build();
        }
    }

}
