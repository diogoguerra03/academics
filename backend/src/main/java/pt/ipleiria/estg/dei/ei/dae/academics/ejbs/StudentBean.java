package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import java.util.List;

@Stateless
public class StudentBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private CourseBean courseBean;

    private Hasher hasher;

    public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(s.username) FROM Student s WHERE s.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }
    public Student create(
            String username, String password, String name, String email, long courseCode
    ) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        if (exists(username)) {
            throw new MyEntityExistsException(
                    "Student with username '" + username + "' already exists");
        }
        var course = courseBean.find(courseCode);
        if (course == null) {
            throw new MyEntityNotFoundException(
                    "Course with code '" + courseCode + "' not found"
            );
        }

        Student student = null;

        try {
            student = new Student(username, hasher.hash(password), name, email,
                    course);
            entityManager.persist(student);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        course.addStudent(student);
        return student;
    }

    public List<Student> getAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllStudents", Student.class).getResultList();
    }

    public Student find(String username) {
        return entityManager.find(Student.class, username);
    }

    public void enrollStudentInSubject(String username, long subjectCode) {
        // Find the student by username
        Student student = find(username);
        if (student == null) {
            throw new IllegalArgumentException("Student with username " + username + " not found.");
        }

        // Find the subject by subject code
        Subject subject = entityManager.find(Subject.class, subjectCode);
        if (subject == null) {
            throw new IllegalArgumentException("Subject with code " + subjectCode + " not found.");
        }

        // Enroll the student in the subject
        subject.addStudent(student);
    }

    public void unrollStudentInSubject(String username, long subjectCode) {
        // Find the student by username
        Student student = entityManager.find(Student.class, username);
        if (student == null) {
            throw new IllegalArgumentException("Student with username " + username + " not found.");
        }

        // Find the subject by subject code
        Subject subject = entityManager.find(Subject.class, subjectCode);
        if (subject == null) {
            throw new IllegalArgumentException("Subject with code " + subjectCode + " not found.");
        }

        // Enroll the student in the subject
        subject.removeStudent(student);
    }

    public Student getStudentWithSubjects(String username) {
        Student student = find(username);
        if (student != null) {
            Hibernate.initialize(student.getSubjects());
        }
        return student;
    }

    public void remove(String username) throws MyEntityNotFoundException {
        Student student = find(username);
        if (student == null) {
            throw new MyEntityNotFoundException(
                    "Student with username '" + username + "' not found"
            );
        }
        entityManager.remove(student);

    }

    public void update(String username, String password, String name, String email, long courseCode) throws MyEntityNotFoundException {
        Student student = entityManager.find(Student.class, username);
        if (student == null) {
            throw new MyEntityNotFoundException(
                    "Student with username '" + username + "' not found"
            );
        }
        entityManager.lock(student, LockModeType.OPTIMISTIC);
        student.setPassword(password);
        student.setName(name);
        student.setEmail(email);
        if (student.getCourse().getCode() != courseCode) {
            Course course = entityManager.find(Course.class, courseCode);
            if (course == null) {
                throw new MyEntityNotFoundException(
                        "Course with code '" + courseCode + "' not found"
                );
            }
            student.setCourse(course);
        }
    }
}
