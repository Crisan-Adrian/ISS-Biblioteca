package persistancy.book;

import model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BookDBRepo implements IBookRepo {
    private SessionFactory sessionFactory;

    public BookDBRepo(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book Save(Book entity) {
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
    public boolean Update(Book entity) {

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
    public boolean Delete(Book entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.createQuery("DELETE Subscriber WHERE id = :id")
                        .setParameter("id", entity.getId());
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
    public Book Find(Book entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Book book =
                        session.createQuery("FROM Book WHERE id = :id", Book.class)
                                .setParameter("id", entity.getId())
                                .setMaxResults(1)
                                .uniqueResult();
                tx.commit();
                return book;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        }
    }

    @Override
    public List<Book> findByISBN(long ISBN) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Book> books =
                        session.createQuery("FROM Book WHERE isbn = :isbn", Book.class)
                                .setParameter("isbn", ISBN).list();
                tx.commit();
                return books;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        }
    }

    @Override
    public List<Book> findByAuthor(String author) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Book> books =
                        session.createQuery("FROM Book WHERE author LIKE :author", Book.class)
                                .setParameter("author", author).list();
                tx.commit();
                return books;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        }
    }

    @Override
    public List<Book> findByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Book> books =
                        session.createQuery("FROM Book WHERE bookName = :name", Book.class)
                                .setParameter("name", name).list();
                tx.commit();
                return books;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
                return null;
            }
        }
    }
}
