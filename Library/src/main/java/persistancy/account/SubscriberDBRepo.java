package persistancy.account;

import model.Employee;
import model.Subscriber;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SubscriberDBRepo implements ISubscriberRepo {
    private SessionFactory sessionFactory;

    public SubscriberDBRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Subscriber Save(Subscriber entity) {
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
    public boolean Update(Subscriber entity) {

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
    public boolean Delete(Subscriber entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.createQuery("DELETE Subscriber WHERE username LIKE :username")
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
    public Subscriber Find(Subscriber entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Subscriber account =
                        session.createQuery("FROM Subscriber WHERE username LIKE :username", Subscriber.class)
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
