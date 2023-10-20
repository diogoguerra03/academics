package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SubjectDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.TeacherDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.TeacherBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Teacher;

import java.util.List;
import java.util.stream.Collectors;

@Path("teachers") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class TeacherService {
    @EJB
    private TeacherBean teacherBean;

    private TeacherDTO toDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getUsername(),
                teacher.getPassword(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getOffice()
        );
    }

    private List<TeacherDTO> toDTOs(List<Teacher> teachers) {
        return teachers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<TeacherDTO> getAllTeachers() {
        return toDTOs(teacherBean.getAll());
    }

    @GET
    @Path("{username}/subjects")
    public Response getTeacherSubjects(@PathParam("username") String username) {
        var teacher = teacherBean.getTeacherWithSubjects(username);
        if (teacher != null) {
            var dtos = subjectsToDTOs(teacher.getSubjects());
            return Response.ok(dtos).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_TEACHER")
                .build();
    }

    @GET
    @Path("{username}")
    public Response getTeacherDetails(@PathParam("username") String username) {
        var teacher = teacherBean.find(username);
        if (teacher != null) {
            return Response.ok(toDTO(teacher)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_TEACHER")
                .build();
    }

    public List<SubjectDTO> subjectsToDTOs(List<Subject> subjects) {
        return subjects.stream().map(this::toDTO).collect(Collectors.toList());
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

    @POST
    @Path("/")
    public Response createNewTeacher (TeacherDTO teacherDTO){
        teacherBean.create(
                teacherDTO.getUsername(),
                teacherDTO.getPassword(),
                teacherDTO.getName(),
                teacherDTO.getEmail(),
                teacherDTO.getOffice()
        );

        Teacher newTeacher = teacherBean.find(teacherDTO.getUsername());
        if(newTeacher == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return  Response.status(Response.Status.CREATED).entity(toDTO(newTeacher)).build();
    }

    @PUT
    @Path("{username}")
    public Response updateTeacher(@PathParam("username") String username, TeacherDTO teacherDTO) {
        var teacher = teacherBean.find(username);
        if (teacher == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        teacherBean.update(username, teacherDTO.getPassword(), teacherDTO.getName(), teacherDTO.getEmail(), teacherDTO.getOffice());
        return Response.ok().build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteTeacher(@PathParam("username") String username) {
        var teacher = teacherBean.find(username);
        if (teacher == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        teacherBean.remove(username);
        return Response.ok().build();
    }
}
