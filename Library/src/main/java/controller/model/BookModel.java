package controller.model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookModel {
    private SimpleLongProperty bookID;
    private SimpleStringProperty bookName;
    private SimpleStringProperty bookAuthor;
    private SimpleLongProperty bookISBN;

    public BookModel(Long bookID, String bookName, String bookAuthor, Long bookISBN) {
        this.bookID = new SimpleLongProperty(bookID);
        this.bookName = new SimpleStringProperty(bookName);
        this.bookAuthor = new SimpleStringProperty(bookAuthor);
        this.bookISBN = new SimpleLongProperty(bookISBN);
    }

    public long getBookID() {
        return bookID.get();
    }

    public void setBookID(long bookID) {
        this.bookID = new SimpleLongProperty(bookID);
    }

    public String getBookName() {
        return bookName.get();
    }

    public void setBookName(String bookName) {
        this.bookName = new SimpleStringProperty(bookName);
    }

    public String getBookAuthor() {
        return bookAuthor.get();
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = new SimpleStringProperty(bookAuthor);
    }

    public Long getBookISBN() {
        return bookISBN.get();
    }

    public void setBookISBN(Long bookISBN) {
        this.bookISBN = new SimpleLongProperty(bookISBN);
    }
}
