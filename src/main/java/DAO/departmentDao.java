package DAO;


import java.util.List;
import java.util.UUID;

import javax.management.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Department;

public class departmentDao {

    public UUID saveDepartment(Department department, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(department);
            transaction.commit();
            return department.getId();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    public Department getDepartmentById(UUID id, Session session) {
        try {
            return session.get(Department.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Department> getAllDepartments(Session session) {
        try {
            org.hibernate.query.Query<Department> query = session.createQuery("FROM Department", Department.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteDepartment(UUID id, Session session) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Department department = session.get(Department.class, id);
            if (department != null) {
                session.delete(department);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }
}

