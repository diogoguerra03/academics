package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentDTO implements Serializable {
    private String username;
    private String password;
    private String name;
    private String email;

    private long courseCode; // Novo atributo courseCode
    private String courseName; // Novo atributo courseName

    private List<SubjectDTO> subjects; // Novo atributo subjects

    // Default no-argument constructor
    public StudentDTO() {
        this.subjects = new ArrayList<>();
    }

    // Constructor with all property values
    public StudentDTO(String username, String password, String name, String email, long courseCode, String courseName, List<SubjectDTO> subjects) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjects = subjects;
    }

    public StudentDTO(String username, String password, String name, String email, long courseCode, String courseName) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjects = new ArrayList<>();
    }

    // Getters and setters for each property
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(long courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }


}