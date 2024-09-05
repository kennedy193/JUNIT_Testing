import static org.junit.Assert.assertNotNull;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Configurations.HibernateUtil;
import DAO.StudentDao;
import model.Student;
import java.util.UUID;

public class SaveStudent {

    private StudentDao studentDao;
    private int studentId;

    @Before
    public void setUp() {
        studentDao = new StudentDao();
    }

    @Test
    public void testSaveStudent() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            
            Student student = new Student();
            student.setFirstName("Jane");
            student.setLastName("Smith");

            studentId = studentDao.saveStudent(student, session);
            transaction.commit();

            assertNotNull(studentId);
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
