package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;

import java.util.List;

@Stateless
public class SubjectBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code, String name, long courseCode, int courseYear, int scholarYear) {
        // search for the course
        Course course = entityManager.find(Course.class, courseCode);
        if (course == null) {
            throw new IllegalArgumentException("Course with code " + courseCode + " not found.");
        }

        var subject = new Subject(code, name, course, courseYear, scholarYear);
        course.addSubject(subject);
        entityManager.persist(subject);

    }

    public List<Subject> getAll() {
        return entityManager.createNamedQuery("getAllSubjects", Subject.class).getResultList();
    }

    public Subject find(long code) {
        return entityManager.find(Subject.class, code);
    }

    // No SubjectBean
    public List<Subject> getByCourseCode(long courseCode) {
        // Implemente a lógica para recuperar as disciplinas por código de curso e retorne a lista.
        // Por exemplo:
        return entityManager.createQuery("SELECT s FROM Subject s WHERE s.course.code = :courseCode", Subject.class)
                .setParameter("courseCode", courseCode)
                .getResultList();
    }

}
