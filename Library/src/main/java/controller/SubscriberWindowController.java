package controller;

import controller.model.BookModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.Subscriber;
import service.SubscriberService;

import java.util.List;

import static controller.SearchTypes.*;

public class SubscriberWindowController {
    @FXML
    private TextField searchInput;
    @FXML
    private ChoiceBox searchType;

    @FXML
    private TableView<BookModel> books;
    @FXML
    private TableColumn<Book, Long> bookID;
    @FXML
    private TableColumn<Book, String> bookName;
    @FXML
    private TableColumn<Book, String> bookAuthor;
    @FXML
    private TableColumn<Book, Long> bookISBN;
    private SubscriberService service;
    private Subscriber subscriber;

    private final ObservableList<BookModel> bookModels = FXCollections.observableArrayList();

    public void setup(SubscriberService service, Subscriber subscriber) {
        this.service = service;
        this.subscriber = subscriber;
        searchType.getItems().addAll(NAME, AUTHOR, ISBN);
        searchType.getSelectionModel().clearAndSelect(0);
    }

    public void Search() {
        switch ((SearchTypes) searchType.getSelectionModel().getSelectedItem()) {
            case NAME -> {
                SearchByName(searchInput.getText());
            }
            case AUTHOR -> {
                SearchByAuthor(searchInput.getText());
            }
            case ISBN -> {
                try {
                    long isbn = Long.parseLong(searchInput.getText());
                    SearchByISBN(isbn);
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please input a number as ISBN");
                    alert.show();
                }
            }
        }
    }

    public void refreshTable(List<Book> availableBooks)
    {
        bookModels.clear();
        for (Book b : availableBooks) {
            bookModels.add(new BookModel(
                    b.getId(),
                    b.getBookName(),
                    b.getAuthor(),
                    b.getIsbn()
            ));
        }
        bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        bookISBN.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        books.setItems(bookModels);
    }

    public void SearchByAuthor(String author) {
        List<Book> availableBooks = service.SearchByAuthor(author);
        refreshTable(availableBooks);
    }

    public void SearchByName(String name) {
        List<Book> availableBooks = service.SearchByName(name);
        refreshTable(availableBooks);
    }

    public void SearchByISBN(long isbn) {
        List<Book> availableBooks = service.SearchByISBN(isbn);
        refreshTable(availableBooks);
    }
}