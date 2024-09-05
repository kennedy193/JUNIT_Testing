

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Configurations.HibernateUtil;
import DAO.SemesterDao;

import model.Semester;
import java.util.UUID;

public class saveSemester {

    private UUID semesterId;
    private SemesterDao semesterDao;

    @Before
    public void setUp() {
        semesterDao = new SemesterDao();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Semester semester = new Semester();
            semester.setSemesterName("Spring 2024");
            semester.setStartDate("2024-01-01");
            semester.setEndDate("2024-05-31");

            semesterDao.saveSemester(semester, session);

            transaction.commit();
            semesterId = semester.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSaveSemester() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Semester savedSemester = session.get(Semester.class, semesterId);
            assertNotNull(savedSemester);
            assertTrue(savedSemester.getSemesterName().equals("Spring 2024"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Semester semester = session.get(Semester.class, semesterId);
            if (semester != null) {
                session.delete(semester);
            }

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
