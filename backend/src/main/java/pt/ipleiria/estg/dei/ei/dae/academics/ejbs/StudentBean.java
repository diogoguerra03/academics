package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Course;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Student;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Subject;

import java.util.List;

@Stateless
public class StudentBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(String username, String password, String name, String email, long courseCode) {
        // 1. Buscar o curso correspondente pelo código do curso
        Course course = entityManager.find(Course.class, courseCode);

        if (course != null) {
            // 2. Criar uma instância do objeto Student passando o curso como parâmetro
            var student = new Student(username, password, name, email, course);

            // 3. Persistir o objeto Student
            entityManager.persist(student);

            // 4. Adicionar o aluno à lista de alunos do curso
            course.addStudent(student);
        } else {
            // Lógica para lidar com curso não encontrado, se necessário.
            throw new IllegalArgumentException("Course with code " + courseCode + " not found.");
        }
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
}
