package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;

import java.util.List;

@Stateless // EJB
public class CourseBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code, String name) {
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

    public void remove(long code) {
        Course course = find(code);
        if (course != null) {
            entityManager.remove(course);
        }
    }

    public void update(Course course) {
        // Use o merge para atualizar o objeto no contexto de persistência
        entityManager.merge(course);
    }
}
