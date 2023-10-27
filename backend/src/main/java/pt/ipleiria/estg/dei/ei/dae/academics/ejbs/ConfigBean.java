package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    @EJB
    private StudentBean studentBean;
    @EJB
    private CourseBean courseBean;

    @EJB
    private SubjectBean subjectBean;

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private TeacherBean teacherBean;

    private Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        courseBean.create(1, "Engenharia informática");
        courseBean.create(2, "Engenharia civil");
        courseBean.create(3, "Engenharia mecânica");
        subjectBean.create(1001, "Programação", 1, 1, 1);
        subjectBean.create(1002, "Análise Matemática", 2, 1, 1);
        subjectBean.create(1003, "Física", 1, 3, 2);
        try {
            studentBean.create("2211027", "123", "Diogo Guerra", "diogo@mail.com", 1);
            studentBean.create("2211028", "123", "Pedro Guerra", "joao@mail.com", 2);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        administratorBean.create("admin", "123", "admin", "admin@mail.com");
        teacherBean.create("2211029", "123", "João Guerra", "joao_guerra@mail.com", "A1");
        teacherBean.create("2211030", "123", "Maria Guerra", "maria@mail.com", "A2");

        studentBean.enrollStudentInSubject("2211027", 1001);
        studentBean.enrollStudentInSubject("2211027", 1003);
        studentBean.enrollStudentInSubject("2211028", 1002);

        teacherBean.associateTeacherToSubject("2211029", 1001);
        teacherBean.associateTeacherToSubject("2211029", 1002);
        teacherBean.associateTeacherToSubject("2211030", 1003);

        teacherBean.dissociateTeacherFromSubject("2211029", 1001);

        studentBean.unrollStudentInSubject("2211027", 1001);


    }
}
