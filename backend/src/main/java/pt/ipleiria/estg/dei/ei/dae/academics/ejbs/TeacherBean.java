package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Teacher;

@Stateless
public class TeacherBean {
    @PersistenceContext
    private EntityManager entityManager;

    //public Teacher(String username, String password, String name, String email) {
    //        super(username, password, name, email);
    //    }

    public void create(String username, String password, String name, String email, String office) {
        entityManager.persist(new Teacher(username, password, name, email, office));
    }
}
