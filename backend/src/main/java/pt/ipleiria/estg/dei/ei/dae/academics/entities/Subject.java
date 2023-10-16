package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(
        name = "subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "course_code", "scholar_year"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllSubjects",
                query = "SELECT s FROM Subject s ORDER BY s.course.name, s.scholarYear DESC, s.courseYear, s.name" // JPQL
        )
})
@Entity
public class Subject implements Serializable {

    @Id
    private long code;
    private String name;
    @ManyToOne
    @JoinColumn(name = "course_code")
    @NotNull
    private Course course;

    @Column(name = "course_year")
    private int courseYear;

    @Column(name = "scholar_year")
    private int scholarYear;
    @ManyToMany
    @JoinTable(
            name = "subjects_students",
            joinColumns = @JoinColumn(
                    name = "subject_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_username",
                    referencedColumnName = "username"
            )
    )
    private List<Student> students;

    @ManyToMany
    @JoinTable(
            name = "subjects_teachers",
            joinColumns = @JoinColumn(
                    name = "subject_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "teacher_username",
                    referencedColumnName = "username"
            )
    )
    private List<Teacher> teachers;

    public Subject() {
        this.students = new ArrayList<>();
    }

    public Subject(long code, String name, Course course, int courseYear, int scholarYear) {
        this.code = code;
        this.name = name;
        this.course = course;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
        this.students = new ArrayList<>();
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public int getScholarYear() {
        return scholarYear;
    }

    public void setScholarYear(int scholarYear) {
        this.scholarYear = scholarYear;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

}
