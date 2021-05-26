package controller;

import controller.model.BookModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Book;
import model.Lend;
import model.Subscriber;
import service.SubscriberService;

import java.util.List;

import static controller.SearchTypes.*;

public class SubscriberWindowController {
    @FXML
    private TextField bookInput;
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

    private Book selectedBook;

    private final ObservableList<BookModel> bookModels = FXCollections.observableArrayList();

    public void setup(SubscriberService service, Subscriber subscriber) {
        this.service = service;
        this.subscriber = subscriber;
        searchType.getItems().addAll(NAME, AUTHOR, ISBN);
        searchType.getSelectionModel().clearAndSelect(0);

        books.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                {
                    if (newValue != null) {
                        selectedBook = new Book(
                                newValue.getBookID(),
                                newValue.getBookISBN(),
                                newValue.getBookName(),
                                newValue.getBookAuthor());
                        bookInput.setText(String.valueOf(newValue.getBookID()));
                        return;
                    }
                    selectedBook = null;
                }
        );
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

    public void noBooksFound(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Search did not get any results");
        alert.show();
    }

    public void SearchByAuthor(String author) {
        List<Book> availableBooks = service.SearchByAuthor(author);
        if(availableBooks.isEmpty())
        {
            noBooksFound();
            return;
        }
        refreshTable(availableBooks);
    }

    public void SearchByName(String name) {
        List<Book> availableBooks = service.SearchByName(name);
        if(availableBooks.isEmpty())
        {
            noBooksFound();
            return;
        }
        refreshTable(availableBooks);
    }

    public void SearchByISBN(long isbn) {
        List<Book> availableBooks = service.SearchByISBN(isbn);
        if(availableBooks.isEmpty())
        {
            noBooksFound();
            return;
        }
        refreshTable(availableBooks);
    }

    public void Lend() {
        if(bookInput.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please input a number as ID");
            alert.show();
            return;
        }
        try {
            long id = Long.parseLong(bookInput.getText());
            Lend lend = service.Lend(id, subscriber.getUsername());
            if(lend != null)
            {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setContentText("Lend Successful. Lend ID: " + lend.getLendID() + " return date is: " + lend.getEnd());
                confirmation.show();
                Search();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Lend Error");
            alert.show();

        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please input a number as ID");
            alert.show();
        }
    }
}
