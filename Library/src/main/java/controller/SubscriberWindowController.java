package controller;

import model.Book;
import service.EmployeeService;
import service.SubscriberService;

import java.util.List;

public class SubscriberWindowController {
    private SubscriberService service;

    public void setup(SubscriberService service){
        this.service = service;
    }

    public List<Book> SearchByAuthor(String author){
        return null;
    }
    public List<Book> SearchByName(String name){
        return null;
    }
    public List<Book> SearchByISBN(long isbn){
        return null;
    }
}
