package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import java.io.Serializable;

public class EmailDTO implements Serializable {
    //  should have a subject and a message as attributes
    private String subject;

    private String message;

    // Default no-argument constructor
    public EmailDTO() {
    }

    // Constructor with all property values
    public EmailDTO(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    // Getters and setters for each property
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
