package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Employee;
import model.Subscriber;
import service.*;

import java.io.IOException;

public class LoginWindowController {

    @FXML
    private TextField usernameInput;
    @FXML
    private TextField passwordInput;

    private ServiceFactory factory;
    private LoginService service;

    public void setup(ServiceFactory factory, LoginService service) {
        this.factory = factory;
        this.service = service;
    }

    public void tryLoginSubscriber() {
        Subscriber subscriber = (Subscriber) service.Login(usernameInput.getText(), passwordInput.getText(), UserType.SUBSCRIBER);
        if (subscriber != null) {
            try {
                Stage stage = (Stage) usernameInput.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/SubscriberWindow.fxml"));
                AnchorPane root = loader.load();

                SubscriberWindowController controller = loader.getController();
                controller.setup((SubscriberService) factory.GetService(UserType.SUBSCRIBER), subscriber);

                Scene scene = new Scene(root, 700, 600);

                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Invalid username and password combination");
            alert.show();
        }
    }

    public void tryLoginEmployee() {
        Employee employee = (Employee)service.Login(usernameInput.getText(), passwordInput.getText(), UserType.EMPLOYEE);
        if (employee != null) {
            try {
                Stage stage = (Stage) usernameInput.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/EmployeeWindow.fxml"));
                AnchorPane root = loader.load();

                EmployeeWindowController controller = loader.getController();
                controller.setup((EmployeeService) factory.GetService(UserType.EMPLOYEE));

                Scene scene = new Scene(root, 700, 600);

                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Invalid username and password combination");
            alert.show();
        }
    }
}
