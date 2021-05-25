package persistancy.book;

import model.Book;
import persistancy.IRepository;

import java.util.List;

public interface IBookRepo extends IRepository<Book> {
    public List<Book> findByISBN(long ISBN);
    public List<Book> findByAuthor(String author);
    public List<Book> findByName(String name);
}
