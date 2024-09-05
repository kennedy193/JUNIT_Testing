

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Configurations.HibernateUtil;
import DAO.CourseDao;

import model.Course;
import java.util.UUID;

public class saveCourse {

    private UUID courseId;
    private CourseDao courseDao;

    @Before
    public void setUp() {
        courseDao = new CourseDao();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Course course = new Course();
            course.setCourseName("Mathematics");
            course.setCourseCode("MATH101");
            

            courseDao.saveCourse(course, session);

            transaction.commit();
            courseId = course.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testSaveCourse() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Course savedCourse = session.get(Course.class, courseId);
            assertNotNull(savedCourse);
            assertEquals("Mathematics", savedCourse.getCourseName());
            assertEquals("MATH101", savedCourse.getCourseCode());
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Course course = session.get(Course.class, courseId);
            if (course != null) {
                session.delete(course);
            }

            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
