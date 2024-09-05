package Configurations;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import model.Student;
import model.Semester;
import model.Department;
import model.Course;
import model.StudentRegistration;
import model.StudentCourse;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "org.postgresql.Driver");
                properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/student_management_db");
                properties.put(Environment.USER, "postgres");
                properties.put(Environment.PASS, "admin");
                properties.put(Environment.HBM2DDL_AUTO, "update");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.FORMAT_SQL, "true");

                configuration.setProperties(properties);

                // Add annotated classes
                configuration.addAnnotatedClass(Student.class);
                configuration.addAnnotatedClass(Semester.class);
                configuration.addAnnotatedClass(Department.class);
                configuration.addAnnotatedClass(Course.class);
                configuration.addAnnotatedClass(StudentRegistration.class);
                configuration.addAnnotatedClass(StudentCourse.class);

                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(registryBuilder.build());

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize Hibernate Session Factory");
            }
        }
        return sessionFactory;
    }

    // Method to get a Hibernate Session for database operations
    public static Session getSession() {
        return getSessionFactory().openSession();
    }

    // Method to obtain a Hibernate Session for database operations (alias for getSession)
    public static Session getConnection() {
        return getSession();
    }

    // Method to shutdown the Hibernate Session Factory
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
