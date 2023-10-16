package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.CourseDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.CourseBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;

import java.util.List;
import java.util.stream.Collectors;

@Path("courses") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class CourseService {
    @EJB
    private CourseBean courseBean;

    private CourseDTO toDTO(Course course) {
        return new CourseDTO(
                course.getCode(),
                course.getName()
        );
    }

    private List<CourseDTO> toDTOs(List<Course> courses) {
        return courses.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/students/”
    public List<CourseDTO> getAllCourses() {
        return toDTOs(courseBean.getAll());
    }

    @GET
    @Path("{code}") // means: the relative url path is “/api/students/{code}”
    public Response getCourseDetails(@PathParam("code") long code) {
        Course course = courseBean.find(code);
        if (course != null) {
            return Response.ok(toDTO(course)).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("ERROR_FINDING_COURSE")
                .build();
    }

    @POST
    @Path("/")
    public Response createNewCourse(CourseDTO courseDTO) {
        courseBean.create(
                courseDTO.getCode(),
                courseDTO.getName()
        );

        Course newCourse = courseBean.find(courseDTO.getCode());
        if (newCourse == null)
            return Response.status(Response.Status.BAD_REQUEST).build();
        return Response.status(Response.Status.CREATED).entity(toDTO(newCourse)).build();
    }

    @DELETE
    @Path("{code}") // Define o caminho para /api/courses/{code}
    public Response deleteCourse(@PathParam("code") long code) {
        Course course = courseBean.find(code);
        if (course == null) {
            // O curso com o código especificado não foi encontrado
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        courseBean.remove(code);
        return Response.status(Response.Status.OK).build();
    }

    @PUT
    @Path("{code}") // Define o caminho para /api/courses/{code}
    public Response updateCourse(@PathParam("code") long code, CourseDTO updatedCourseDTO) {
        // Verifique se o curso com o código especificado existe
        Course existingCourse = courseBean.find(code);
        if (existingCourse == null) {
            // O curso com o código especificado não foi encontrado
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Atualize os dados do curso existente com base nos dados fornecidos em updatedCourseDTO
        existingCourse.setName(updatedCourseDTO.getName());

        // Persista as alterações no banco de dados
        courseBean.update(existingCourse);

        // Retorne uma resposta de sucesso
        return Response.status(Response.Status.OK).entity(toDTO(existingCourse)).build();
    }

}
