package model;

public class Book {
    private long id;
    private long isbn;
    private String bookName;
    private String author;

    public Book() {
    }

    public Book(long bookID) {
        id = bookID;
    }

    public Book(long id, long isbn, String bookName, String author) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = bookName;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
