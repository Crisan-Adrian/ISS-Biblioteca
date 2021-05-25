package persistancy.account;

import model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class EmployeeDBRepo implements IEmployeeRepo {

    private SessionFactory sessionFactory;

    public EmployeeDBRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Employee Save(Employee entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return entity;
    }

    @Override
    public boolean Update(Employee entity) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.update(entity);
                tx.commit();
                return true;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return false;
            }
        }
    }

    @Override
    public boolean Delete(Employee entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.createQuery("DELETE Employee WHERE username LIKE :username")
                                .setParameter("username", entity.getUsername());
                tx.commit();
                return true;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return false;
            }
        }
    }

    @Override
    public Employee Find(Employee entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Employee account =
                        session.createQuery("FROM Employee WHERE username LIKE :username", Employee.class)
                                .setParameter("username", entity.getUsername())
                                .setMaxResults(1)
                                .uniqueResult();
                tx.commit();
                return account;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        }
    }
}
