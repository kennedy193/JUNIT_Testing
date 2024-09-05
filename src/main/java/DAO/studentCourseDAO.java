package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import model.StudentCourse;
import java.util.UUID;

public class studentCourseDAO {

    // Method to save student course marks
    public void saveStudentCourseMarks(UUID courseId, UUID registrationNumber, int marks, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setCourse(courseId);
            studentCourse.setRegistration(registrationNumber);
            studentCourse.setMarksInCourse(marks);

            session.save(studentCourse);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // Method to calculate total semester marks (scaled to 100 marks)
    public int getTotalMarksForSemester(UUID registrationNumber, Session session) {
        try {
            Query<Integer> query = session.createQuery(
                    "SELECT SUM(sc.marksInCourse) FROM StudentCourse sc WHERE sc.registration = :registration",
                    Integer.class);
            query.setParameter("registration", registrationNumber);
            Integer totalMarks = query.uniqueResult();
            if (totalMarks == null) {
                return 0;
            }
            // Scaling to 100 marks (assuming 5 courses, each max 20 marks)
            return totalMarks * 5;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Method to adjust total marks to out of 20
    public int adjustToOutOf20(int totalMarks) {
        return (int) Math.round(((double) totalMarks / 100) * 20);
    }

    // Method to get grade category based on marks out of 20
    public String getGradeCategory(int marksOutOf20) {
        if (marksOutOf20 >= 16) {
            return "High Distinction";
        } else if (marksOutOf20 >= 12) {
            return "Lower Distinction";
        } else if (marksOutOf20 >= 10) {
            return "Pass";
        } else {
            return "Expel";
        }
    }
}
