package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.io.Serializable;

public class TeacherDTO implements Serializable {
    private String username;
    private String password;
    private String name;
    private String email;
    private String office;

    // Default no-argument constructor
    public TeacherDTO() {
    }

    // Constructor with all property values
    public TeacherDTO(String username, String password, String name, String email, String office) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.office = office;
    }

    // Getters and setters for each property
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getters and setters for each property
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getters and setters for each property
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and setters for each property
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getters and setters for each property
    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
