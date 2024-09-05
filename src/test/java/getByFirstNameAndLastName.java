

import static org.junit.Assert.assertEquals;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Configurations.HibernateUtil;
import DAO.StudentDao;

import model.Student;
import java.util.UUID;

public class getByFirstNameAndLastName {

    private int studentId;
    private StudentDao studentDao;

    @Before
    public void setUp() {
        studentDao = new StudentDao();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = new Student();
            student.setFirstName("John");
            student.setLastName("Doe");

            session.save(student);

            transaction.commit();
            studentId = student.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testGetByFirstNameAndLastName() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Student foundStudent = studentDao.getByFirstNameAndLastName("John", "Doe", session);
            assertEquals(studentId, foundStudent.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.delete(student);
            }

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
