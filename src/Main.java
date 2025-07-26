import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.View;


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
