package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class StudentBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(String username) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(s.username) FROM Student s WHERE s.username = :username",
                Long.class
        );
        query.setParameter("username", username);
        return (Long)query.getSingleResult() > 0L;
    }
    public void create(String username, String password, String name, String email,
                       long courseCode) throws MyEntityExistsException, MyEntityNotFoundException {
        if (exists(username)) {
            throw new MyEntityExistsException(
                    "Student with username '" + username + "' already exists");
        }
        Course course = entityManager.find(Course.class, courseCode);
        if (course == null) {
            throw new MyEntityNotFoundException(
                    "Course with code '" + courseCode + "' not found"
            );
        }
        Student student = new Student(username, password, name, email, course);
        entityManager.persist(student);
        course.addStudent(student);
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

    public void remove(String username) {
        Student student = find(username);
        if (student != null) {
            entityManager.remove(student);
        }
    }

    public void update(String username, String password, String name, String email, long courseCode) {
        Student student = entityManager.find(Student.class, username);
        if (student == null) {
            System.err.println("ERROR_STUDENT_NOT_FOUND: " + username);
            return;
        }
        entityManager.lock(student, LockModeType.OPTIMISTIC);
        student.setPassword(password);
        student.setName(name);
        student.setEmail(email);
        if (student.getCourse().getCode() != courseCode) {
            Course course = entityManager.find(Course.class, courseCode);
            if (course == null) {
                System.err.println("ERROR_COURSE_NOT_FOUND: " + courseCode);
                return;
            }
            student.setCourse(course);
        }
    }
}
