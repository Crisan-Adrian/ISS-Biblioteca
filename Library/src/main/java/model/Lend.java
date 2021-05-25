package model;

import java.time.LocalDate;

public class Lend {
    private long lendID;
    private long bookID;
    private String subscriber;
    private LocalDate end;
    private boolean returned;

    public Lend() {
    }

    public Lend(long lendID, long bookID, String subscriber, LocalDate end, boolean returned) {
        this.lendID = lendID;
        this.bookID = bookID;
        this.subscriber = subscriber;
        this.end = end;
        this.returned = returned;
    }

    public long getLendID() {
        return lendID;
    }

    public void setLendID(long lendID) {
        this.lendID = lendID;
    }

    public long getBookID() {
        return bookID;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public boolean isReturned() {
        return returned;
    }

    public boolean getReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
