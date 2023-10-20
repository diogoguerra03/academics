package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Teacher;

import java.util.List;

@Stateless
public class TeacherBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email, String office) {
        entityManager.persist(new Teacher(username, password, name, email, office));
    }

    public Teacher find(String username) {
        return entityManager.find(Teacher.class, username);
    }

    // Associate/dissociate a teacher to/from a subject;
    public void associateTeacherToSubject(String username, long subjectCode) {
        // Find the teacher by username
        Teacher teacher = find(username);
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher with username " + username + " not found.");
        }

        // Find the subject by subject code
        Subject subject = entityManager.find(Subject.class, subjectCode);
        if (subject == null) {
            throw new IllegalArgumentException("Subject with code " + subjectCode + " not found.");
        }

        // Associate the teacher to the subject
        subject.addTeacher(teacher);
    }

    public void dissociateTeacherFromSubject(String username, long subjectCode) {
        // Find the teacher by username
        Teacher teacher = entityManager.find(Teacher.class, username);
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher with username " + username + " not found.");
        }

        // Find the subject by subject code
        Subject subject = entityManager.find(Subject.class, subjectCode);
        if (subject == null) {
            throw new IllegalArgumentException("Subject with code " + subjectCode + " not found.");
        }

        // Dissociate the teacher from the subject
        subject.removeTeacher(teacher);
    }

    public Teacher getTeacherWithSubjects(String username) {

        Teacher teacher = find(username);
        if (teacher != null) {
            Hibernate.initialize(teacher.getSubjects());
        }
        return teacher;
    }

    public List<Teacher> getAll() {
        // remember, maps to: “SELECT s FROM Teacher s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllTeachers", Teacher.class).getResultList();
    }


    public void update(String username, String password, String name, String email, String office) {
        Teacher teacher = entityManager.find(Teacher.class, username);
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher with username " + username + " not found.");
        }

        teacher.setPassword(password);
        teacher.setName(name);
        teacher.setEmail(email);
        teacher.setOffice(office);
    }

    public void remove(String username) {
        Teacher teacher = entityManager.find(Teacher.class, username);
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher with username " + username + " not found.");
        }
        entityManager.remove(teacher);
    }
}
