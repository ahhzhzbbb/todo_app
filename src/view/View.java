package view;


import controller.MainController;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Task;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View {
    public static Scene getLoginView(Stage stage)
    {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #f0f0f0;"); // đổi màu nền VBox
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(View.class.getResource("/style.css").toExternalForm());

        TextField usernameFeild = new TextField();
        TextField passWordFeild = new TextField();
        Button signInButton = new Button("Sign in");
        Button signUpButton = new Button("Sign up");
        Label lb = new Label("Sai tên đăng nhập hoặc mật khẩu");
        Label userFeildLabel = new Label("UserName");
        Label passwordLabel = new Label("Password");
        signInButton.setOpacity(0);

        FadeTransition fade = new FadeTransition(Duration.millis(10000), signInButton);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();


        usernameFeild.setPromptText("Type your username");
        usernameFeild.setStyle("""
				    -fx-font-size: 14px;
				    -fx-background-radius: 10;
				    -fx-border-radius: 10;
				    -fx-border-color: black;
				    -fx-border-width: 2;
				    -fx-padding: 5 10 5 10;
				""");

        passWordFeild.setPromptText("Type your password");
        passWordFeild.setStyle("""
				    -fx-font-size: 14px;
				    -fx-background-radius: 10;
				    -fx-border-radius: 10;
				    -fx-border-color: black;
				    -fx-border-width: 2;
				    -fx-padding: 5 10 5 10;
				""");

        VBox usernameVBox = new VBox(10);
        VBox passwordVBox = new VBox(10);
        usernameVBox.getChildren().addAll(userFeildLabel, usernameFeild);
        passwordVBox.getChildren().addAll(passwordLabel, passWordFeild);


        signInButton.setOnAction(e ->{
            String userNameInput = usernameFeild.getText();
            String userPassWord = passWordFeild.getText();

            if(MainController.loginController(stage, userNameInput, userPassWord))
            {
                System.out.println("ok");
            }
            else {
                root.getChildren().add(lb);
            }
        });

        signUpButton.setOnAction(e ->{
            MainController.signInUpController(stage, false);
        });

        root.getChildren().addAll(usernameVBox, passwordVBox, signInButton, signUpButton);
        return scene;
    }

    public static Scene getMainView(String username)
    {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 800, 600);
        root.setStyle("-fx-background-color: #000000;"); // đổi màu nền VBox
        Button addTaskButton = new Button("+");
        addTaskButton.setStyle("-fx-pref-width: 150; -fx-pref-height: 50; -fx-background-color: #1DEFFE");
        root.getChildren().addAll(addTaskButton);

        VBox allTasks = showAllTaskOfUser(username);
        root.getChildren().add(allTasks);

        addTaskButton.setOnAction(e ->{
            TextField titleFeild = new TextField();
            TextField newTaskField = new TextField();
            Button removeTaskButton = new Button("-");
            newTaskField.setPromptText("Type your task...");
            newTaskField.setStyle("""
				    -fx-font-size: 14px;
				    -fx-background-radius: 10;
				    -fx-border-radius: 10;
				    -fx-border-color: white;
				    -fx-border-width: 2;
				    -fx-padding: 5 10 5 10;
				""");

            titleFeild.setPromptText("Title");
            newTaskField.setStyle("""
				    -fx-font-size: 14px;
				    -fx-background-radius: 10;
				    -fx-border-radius: 10;
				    -fx-border-color: white;
				    -fx-border-width: 2;
				    -fx-padding: 5 10 5 10;
				""");
            VBox taskVBox = new VBox(0);
            taskVBox.getChildren().addAll(titleFeild, newTaskField);
            taskVBox.requestFocus();
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("ENTER được nhấn");
                    String newTaskInput = newTaskField.getText();
                    String titleInput = titleFeild.getText();
                    MainController.addNewTask(newTaskInput, titleInput, username);
                }

                root.getChildren().clear();
                root.getChildren().addAll(addTaskButton, showAllTaskOfUser(username));

            });
            root.getChildren().add(taskVBox);
        });



        return scene;
    }

    public static VBox showAllTaskOfUser(String username)
    {
        //load các tasks trong db của user
        VBox root = new VBox();
        List<Task> list;
        list = MainController.showAllTask(username);
        for(Task temp : list)
        {
            Text titleText = new Text(temp.getTitle());
            titleText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            titleText.setFill(Color.GREEN);

            Text descriptionText = new Text(temp.getDescription());
            descriptionText.setFill(Color.WHITE);
            descriptionText.setWrappingWidth(300); // nếu mô tả dài thì bọc dòng

            VBox textVBox = new VBox(5); // khoảng cách giữa các dòng text
            textVBox.setStyle("-fx-background-color: #2c3e50; -fx-padding: 10; -fx-background-radius: 10;");
            textVBox.getChildren().addAll(titleText, descriptionText);

            root.getChildren().add(textVBox); // root là VBox chứa toàn bộ task
        }
        return root;
    }
    public static Scene getSignUpScene(Stage stage)
    {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(View.class.getResource("/style.css").toExternalForm());

        TextField usernameFeild = new TextField();
        TextField passWordFeild = new TextField();
        TextField passWordRetypeFeild = new TextField();
        Button goBackButton = new Button("Go back");
        Button signUpButton = new Button("Sign up");
        Label userFeildLabel = new Label("UserName");
        Label passwordLabel = new Label("Password");
        Label passwordRetypeLabel = new Label("Check your password");

        usernameFeild.setPromptText("Type your username");
        usernameFeild.setStyle("""
				    -fx-font-size: 14px;
				    -fx-background-radius: 10;
				    -fx-border-radius: 10;
				    -fx-border-color: white;
				    -fx-border-width: 2;
				    -fx-padding: 5 10 5 10;
				""");

        passWordFeild.setPromptText("Type your password");
        passWordFeild.setStyle("""
				    -fx-font-size: 14px;
				    -fx-background-radius: 10;
				    -fx-border-radius: 10;
				    -fx-border-color: white;
				    -fx-border-width: 2;
				    -fx-padding: 5 10 5 10;
				""");

        passWordRetypeFeild.setPromptText("ReType your password");
        passWordRetypeFeild.setStyle("""
				    -fx-font-size: 14px;
				    -fx-background-radius: 10;
				    -fx-border-radius: 10;
				    -fx-border-color: white;
				    -fx-border-width: 2;
				    -fx-padding: 5 10 5 10;
				""");

        VBox usernameVBox = new VBox(10);
        VBox passwordVBox = new VBox(10);
        VBox passwordRetypeVBox = new VBox(10);
        usernameVBox.getChildren().addAll(userFeildLabel, usernameFeild);
        passwordVBox.getChildren().addAll(passwordLabel, passWordFeild);
        passwordRetypeVBox.getChildren().addAll(passwordRetypeLabel, passWordRetypeFeild);

        HBox buttonsHBox = new HBox(350);
        buttonsHBox.getChildren().addAll(goBackButton, signUpButton);

        goBackButton.setOnAction(e ->{
            MainController.signInUpController(stage, true);
        });

        signUpButton.setOnAction(e ->{
            String userNameInput = usernameFeild.getText();
            String userPassWord = passWordFeild.getText();
            String userPassWordRetype = passWordRetypeFeild.getText();
            if(passwordRetypeVBox.getChildren().getLast() instanceof Label label)
            {
                passwordRetypeVBox.getChildren().remove(passwordRetypeVBox.getChildren().size() - 1);
            }
            if(userPassWordRetype.equals(userPassWord))
            {
                Node lastNode = root.getChildren().getLast();
                if(lastNode instanceof Label label)
                {
                    root.getChildren().remove(root.getChildren().size() - 1);
                }

                if(MainController.signUpController(userNameInput, userPassWord))
                {
                    Label  successLabel = new Label("Đã đăng ký thành công!");
                    root.getChildren().add(successLabel);
                }
                else{
                    Label  failLabel = new Label("Tài khoản hiện đã tồn tại...");
                    root.getChildren().add(failLabel);
                }
            }
            else {
                passwordRetypeVBox.getChildren().add(new Label("Incorrect Password!!!"));
            }
        });

        root.getChildren().addAll(usernameVBox, passwordVBox, passwordRetypeVBox, buttonsHBox);
        return scene;

    }
}
