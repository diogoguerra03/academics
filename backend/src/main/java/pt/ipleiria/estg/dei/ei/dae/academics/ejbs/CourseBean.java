package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless // EJB
public class CourseBean {

    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(long code) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(c.code) FROM Course c WHERE c.code = :code",
                Long.class
        );
        query.setParameter("code", code);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(long code, String name) throws MyEntityExistsException {
        if (exists(code)) {
            throw new MyEntityExistsException(
                    "Course with code '" + code + "' already exists");
        }

        var course = new Course(code, name);
        entityManager.persist(course);
    }

    public List<Course> getAll() {
        // remember, maps to: “SELECT c FROM Course c ORDER BY c.name”
        return entityManager.createNamedQuery("getAllCourses", Course.class).getResultList();
    }

    public Course find(long code) {
        return entityManager.find(Course.class, code);
    }

    public void remove(long code) throws MyEntityNotFoundException {
        Course course = find(code);
        if (course == null) {
            throw new MyEntityNotFoundException(
                    "Course with code '" + code + "' not found"
            );
        }
        entityManager.remove(course);

    }

    public void update(Course course) throws MyEntityNotFoundException {

        if (course == null) {
            throw new MyEntityNotFoundException(
                    "Course not found"
            );
        }

        // Use o merge para atualizar o objeto no contexto de persistência
        entityManager.merge(course);
    }
}
