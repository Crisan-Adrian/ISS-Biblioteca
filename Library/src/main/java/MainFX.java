import controller.LoginWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import persistancy.account.EmployeeDBRepo;
import persistancy.account.IEmployeeRepo;
import persistancy.account.ISubscriberRepo;
import persistancy.account.SubscriberDBRepo;
import persistancy.book.BookDBRepo;
import persistancy.book.IBookRepo;
import persistancy.lend.ILendRepo;
import persistancy.lend.LendDBRepo;
import service.LoginService;
import service.ServiceFactory;

public class MainFX  extends Application {

    private static SessionFactory sessionFactory;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();
            try {
                sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            }
            catch (Exception e) {
                System.err.println("Exception "+e);
                StandardServiceRegistryBuilder.destroy( registry );
            }

            IEmployeeRepo employeeRepo = new EmployeeDBRepo(sessionFactory);
            ISubscriberRepo subscriberRepo = new SubscriberDBRepo(sessionFactory);
            IBookRepo bookRepo = new BookDBRepo(sessionFactory);
            ILendRepo lendRepo = new LendDBRepo(sessionFactory);

            LoginService loginService = new LoginService(employeeRepo, subscriberRepo);
            ServiceFactory serviceFactory = new ServiceFactory(lendRepo, bookRepo, subscriberRepo);

            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getResource("views/LoginWindow.fxml"));
            AnchorPane root=loader.load();

            LoginWindowController controller = loader.getController();
            controller.setup(serviceFactory, loginService);

            Scene scene = new Scene(root, 700, 600);

            primaryStage.initStyle(StageStyle.DECORATED);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Library");
            primaryStage.show();
            root.requestFocus();

            primaryStage.setOnCloseRequest(e -> {
                if ( sessionFactory != null ) {
                    sessionFactory.close();
                }
            });

        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
