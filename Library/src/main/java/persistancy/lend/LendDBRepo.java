package persistancy.lend;

import model.Lend;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class LendDBRepo implements ILendRepo {

    private SessionFactory sessionFactory;

    public LendDBRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Lend Save(Lend entity) {
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
    public boolean Update(Lend entity) {

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
    public boolean Delete(Lend entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.delete(entity);
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
    public Lend Find(Lend entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Lend lend =
                        session.createQuery("FROM Lend WHERE lendID = :id", Lend.class)
                                .setParameter("id", entity.getLendID())
                                .setMaxResults(1)
                                .uniqueResult();
                tx.commit();
                return lend;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        }
    }

    @Override
    public List<Long> FindActiveLends() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Lend> lends =
                        session.createQuery("SELECT lendID FROM Lend WHERE isReturned = false", Lend.class)
                                .list();
                tx.commit();
                return lends.stream().map(Lend::getLendID).collect(Collectors.toList());
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        }
    }

    @Override
    public Lend FindBookLends(Long bookID) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Lend lend =
                        session.createQuery("FROM Lend WHERE bookID = :book AND isReturned = false", Lend.class)
                                .setParameter("book", bookID)
                                .setMaxResults(1)
                                .uniqueResult();
                tx.commit();
                return lend;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        }
    }
}
