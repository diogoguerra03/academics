package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SubjectDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.SubjectBean;
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

    private List<SubjectDTO> toDTOs(List<Subject> subjects) {
        return subjects.stream().map(this::toDTO).collect(java.util.stream.Collectors.toList());
    }

    @GET
    @Path("/") // means: the relative url path is “/api/students/”
    public List<SubjectDTO> getAllSubjects() {
        return toDTOs(subjectBean.getAll());
    }

    @GET
    @Path("course/{courseCode}") // Por exemplo, "/api/subjects/course/1" para obter as disciplinas do curso com código 1.
    public List<SubjectDTO> getSubjectsByCourseCode(@PathParam("courseCode") long courseCode) {
        List<Subject> subjects = subjectBean.getByCourseCode(courseCode); // Suponha que você tenha um método para buscar as disciplinas por código de curso.
        return toDTOs(subjects);
    }

}
