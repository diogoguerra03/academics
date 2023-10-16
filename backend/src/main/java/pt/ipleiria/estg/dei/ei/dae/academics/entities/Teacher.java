package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Entity
public class Teacher extends User {

    private String office;

    @ManyToMany(mappedBy = "teachers")
    private List<Subject> subjects;

    public Teacher() {
    }

    public Teacher(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
}
