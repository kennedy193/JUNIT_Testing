package DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import model.Student;
import java.util.UUID;

public class StudentDao {

    public int saveStudent(Student student, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            return student.getId();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return -1; // Return -1 or handle the retry logic appropriately
        }
    }

    public Student getByFirstNameAndLastName(String firstName, String lastName, Session session) {
        try {
            return session.createQuery("FROM Student WHERE firstName = :firstName AND lastName = :lastName", Student.class)
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerStudentToSemesterAndDepartment(int studentId, UUID semesterId, UUID departmentId, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Retrieve the student
            Student student = session.get(Student.class, studentId);
            if (student == null) {
                System.out.println("Student with ID " + studentId + " not found.");
                return false;
            }

            // Set the semester and department for the student
            student.setSemesterId(semesterId);
            student.setDepartmentId(departmentId);

            session.update(student);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}
