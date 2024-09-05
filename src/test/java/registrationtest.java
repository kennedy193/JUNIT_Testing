import static org.junit.Assert.assertTrue;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Configurations.HibernateUtil;
import DAO.SemesterDao;
import DAO.StudentDao;
import model.Student;
import model.Semester;
import java.util.UUID;

public class registrationtest {

    private StudentDao studentDao;
    private SemesterDao semesterDao;
    private int studentId;
    private UUID semesterId;
    private UUID departmentId;

    @Before
    public void setUp() {
        studentDao = new StudentDao();
        semesterDao = new SemesterDao();

        // Prepare the database with a semester and department
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Semester semester = new Semester();
           
            semesterId = semesterDao.saveSemester(semester, session);

            // Assuming departmentId is obtained similarly
            departmentId = UUID.randomUUID(); // Replace with actual retrieval

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testRegisterStudentToSemesterAndDepartment() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Student student = new Student();
            student.setFirstName("Jane");
            student.setLastName("Smith");
            studentId = studentDao.saveStudent(student, session);

            boolean registrationResult = studentDao.registerStudentToSemesterAndDepartment(studentId, semesterId, departmentId, session);
            assertTrue(registrationResult);

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Delete the semester
            Semester semester = session.get(Semester.class, semesterId);
            if (semester != null) {
                session.delete(semester);
            }

            transaction.commit();

            // Delete the student
            transaction = session.beginTransaction();
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
