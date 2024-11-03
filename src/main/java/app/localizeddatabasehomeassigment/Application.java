package app.localizeddatabasehomeassigment;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 250);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

        // Get the controller from the FXMLLoader
        Controller controller = fxmlLoader.getController();
        controller.setStage(stage); // Pass the stage to the controller

        stage.setTitle("Employee Management"); // Initial title
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
