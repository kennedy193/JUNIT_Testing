

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import DAO.SemesterDao;

public class GradeCategoryTest {

    @Test
    public void testGetGradeCategory() {
        SemesterDao dao = new SemesterDao();

        // Test cases with different marks
       /* assertEquals("High Distinction", dao.getGradeCategory(18));
        assertEquals("Lower Distinction", dao.getGradeCategory(13));
        assertEquals("Pass", dao.getGradeCategory(10));
        assertEquals("Expel", dao.getGradeCategory(5));*/
    }
}
