package controller;

import com.sun.javafx.scene.control.InputField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import service.EmployeeService;
import service.LibraryException;

import java.time.LocalDate;
import java.util.Optional;

public class EmployeeWindowController {
    @FXML
    private DatePicker newReturnDate;
    @FXML
    private Label returnDateLabel;
    @FXML
    private Label currentDateLabel;
    @FXML
    private TextField bookInput;
    private EmployeeService service;

    public void setup(EmployeeService service) {
        this.service = service;
    }


    public void CheckBook(ActionEvent actionEvent) {
        if (bookInput.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please input a number as book ID");
            a.show();
            return;
        }
        try {
            long id = Long.parseLong(bookInput.getText());
            LocalDate returnDate = service.GetLend(id);
            returnDateLabel.setText(returnDate.toString());
            currentDateLabel.setText(LocalDate.now().toString());
        } catch (NumberFormatException ex) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please input a number as book ID");
            a.show();
            returnDateLabel.setText("");
        } catch (LibraryException libraryException) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(libraryException.getLocalizedMessage());
            a.show();
            returnDateLabel.setText("");
        }
    }

    public void ExtendLend(ActionEvent actionEvent) {
        if (bookInput.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please input a number as book ID");
            a.show();
            return;
        }
        if (newReturnDate.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please select a new return date");
            a.show();
            return;
        }
        try {
            LocalDate newDate = newReturnDate.getValue();
            long id = Long.parseLong(bookInput.getText());
            service.ExtendLend(id, newDate);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("New return date is: " + newDate.toString());
            a.show();
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please input a number as book ID");
            a.show();
        } catch (LibraryException ex) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(ex.getLocalizedMessage());
            a.show();
        }
    }


    public void AcceptReturn(ActionEvent actionEvent) {
        if (bookInput.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please input a number as book ID");
            a.show();
            return;
        }
        try {
            long id = Long.parseLong(bookInput.getText());
            service.ReturnLend(id);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("Book returned");
            a.show();
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please input a number as book ID");
            a.show();
        } catch (LibraryException ex) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(ex.getLocalizedMessage());
            a.show();
        }
    }

    public void ApplyPenalty(ActionEvent actionEvent) {
        if (bookInput.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please input a number as book ID");
            a.show();
            return;
        } try {
            long id = Long.parseLong(bookInput.getText());
            Dialog<String> penaltyDialog = new Dialog<>();
            penaltyDialog.setTitle("Select penalty");

            ButtonType generateReport = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            penaltyDialog.getDialogPane().getButtonTypes().addAll(generateReport, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField stringInput = new TextField();

            stringInput.setPromptText("Penalty...");

            grid.add(stringInput, 0, 0);

            Node generateReportButton = penaltyDialog.getDialogPane().lookupButton(generateReport);
            generateReportButton.setDisable(true);

            stringInput.textProperty()
                    .addListener(observable -> generateReportButton.setDisable(stringInput.getText().equals("")));

            penaltyDialog.getDialogPane().setContent(grid);

            penaltyDialog.setResultConverter(dialogButton -> {
                if (dialogButton == generateReport) {
                    return stringInput.getText();
                }
                return "";
            });

            Optional<String> result = penaltyDialog.showAndWait();

            if (result.isPresent()) {
                String penalty = result.get();
                service.ApplyPenalty(id, penalty);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Book returned. Penalty Applied: " + penalty);
                a.show();
            }
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Please input a number as book ID");
            a.show();
        } catch (LibraryException ex) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText(ex.getLocalizedMessage());
            a.show();
        }
    }
}
