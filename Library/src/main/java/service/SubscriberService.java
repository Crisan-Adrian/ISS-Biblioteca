package service;

import model.Book;
import model.Lend;
import persistancy.book.IBookRepo;
import persistancy.lend.ILendRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SubscriberService {
    private final IBookRepo bookRepo;
    private final ILendRepo lendRepo;

    public SubscriberService(ILendRepo lendRepo, IBookRepo bookRepo) {
        this.bookRepo = bookRepo;
        this.lendRepo = lendRepo;
    }

    public List<Book> SearchByName(String name){
        List<Book> books = bookRepo.findByName(name);
        return getAvailableBooks(books);
    }

    public List<Book> SearchByAuthor(String author){
        List<Book> books = bookRepo.findByAuthor(author);
        return getAvailableBooks(books);
    }

    public List<Book> SearchByISBN(long isbn){
        List<Book> books = bookRepo.findByISBN(isbn);
        return getAvailableBooks(books);
    }

    private List<Book> getAvailableBooks(List<Book> books) {
        List<Long> lentBooks = lendRepo.FindActiveLends();

        List<Book> availableBooks = new ArrayList<>();
        for(Book b : books)
        {
            if(!lentBooks.contains(b.getId()))
            {
                availableBooks.add(b);
            }
        }
        return availableBooks;
    }

    public Lend Lend(long bookID, String subscriber) {
        Book b = bookRepo.Find(new Book(bookID));

        if(b != null) {
            Lend l = new Lend();
            l.setBookID(bookID);
            l.setSubscriber(subscriber);
            l.setEnd(LocalDate.now().plusDays(14));
            l.setReturned(false);
            l = lendRepo.Save(l);
            return l;
        }
        return null;
    }
}
