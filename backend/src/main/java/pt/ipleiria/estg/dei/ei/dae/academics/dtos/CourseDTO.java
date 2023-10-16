package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.io.Serializable;

public class CourseDTO implements Serializable {

    private long code;
    private String name;

    public CourseDTO() {
    }

    public CourseDTO(long code, String name) {
        this.code = code;
        this.name = name;
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
}
