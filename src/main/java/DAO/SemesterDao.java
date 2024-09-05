package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import model.Semester;
import java.util.UUID;

public class SemesterDao {

    public UUID saveSemester(Semester semester, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(semester);
            transaction.commit();
            return semester.getId();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

}
