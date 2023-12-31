package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubjectDTO implements Serializable {
    //attributes code, name, courseCode, courseName,
    //courseYear and scholarYear. Add constructors, getter and setter methods;
    private long code;
    private String name;
    private long courseCode;
    private String courseName;
    private int courseYear;
    private int scholarYear;

    private List<SubjectDTO> subjects;

    // Default no-argument constructor
    public SubjectDTO() {
        this.subjects = new ArrayList<>();
    }

    // Constructor with all property values
    public SubjectDTO(long code, String name, long courseCode, String courseName, int courseYear, int scholarYear, List<SubjectDTO> subjects) {
        this.code = code;
        this.name = name;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
        this.subjects = subjects;
    }

    public SubjectDTO(long code, String name, long courseCode, String courseName, int courseYear, int scholarYear) {
        this.code = code;
        this.name = name;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
        this.subjects = new ArrayList<>();
    }

    // Getters and setters for each property

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

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }
}
