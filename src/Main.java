import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Scene loginScene = View.getLoginView(stage);
        stage.setScene(loginScene);

        stage.setTitle("Hello JavaFX");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
