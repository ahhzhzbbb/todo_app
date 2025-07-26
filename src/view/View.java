package view;


import controller.MainController;
import controller.SoundController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Task;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;


import java.util.List;
import java.util.Objects;

public class View {
    public static Scene getLoginView(Stage stage)
    {
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #f0f0f0;"); // đổi màu nền VBox
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("/style.css")).toExternalForm());

        TextField usernameFeild = new TextField();
        TextField passWordFeild = new TextField();
        Button signInButton = new Button("Sign in");
        Button signUpButton = new Button("Sign up");
        Label lb = new Label("Sai tên đăng nhập hoặc mật khẩu");
        Label userFeildLabel = new Label("UserName");
        Label passwordLabel = new Label("Password");


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


        signInButton.setOnAction(_ ->{
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

        signUpButton.setOnAction(_ -> MainController.signInUpController(stage, false));

        root.getChildren().addAll(usernameVBox, passwordVBox, signInButton, signUpButton);
        return scene;
    }

    public static Scene getMainView(Stage stage, String username)
    {
        VBox root = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(scrollPane, 800, 600);
        scene.getStylesheets().add(
                Objects.requireNonNull(View.class.getResource("/MainStyle.css")).toExternalForm()
        );

        //addTaskButton
        Button addTaskButton = new Button("+ Add a task");
        addTaskButton.getStyleClass().add("add-task-btn"); // Dùng class trong MainStyle.css
        Pane addTaskPane = new Pane(addTaskButton);
        addTaskButton.prefWidthProperty().bind(addTaskPane.widthProperty());
        addTaskButton.prefHeightProperty().bind(addTaskPane.heightProperty());
        //
        //
        //
        //
        //
        //taskVBox
        VBox taskVBox = new VBox(0);
        VBox.setVgrow(taskVBox, Priority.ALWAYS);
        taskVBox.prefWidthProperty().bind(addTaskPane.widthProperty());
        taskVBox.prefHeightProperty().bind(addTaskPane.heightProperty());
        TextField titleFeild = new TextField();
        TextField newTaskField = new TextField();
        newTaskField.setPromptText("Type your task...");
        titleFeild.setPromptText("Title");
        taskVBox.getChildren().addAll(titleFeild, newTaskField);

        //signOutButton
        Button signOutButton =  new Button("Sign Out");
        signOutButton.setOnAction(_ -> MainController.signOutController(stage));

        //rootAdd
        root.getChildren().addAll(addTaskPane, signOutButton);

        //allTasks
        VBox allTasks = showAllTaskOfUser(username);
        root.getChildren().add(allTasks);


        //addTask event
        addTaskButton.setOnAction(_ ->{
            SoundController.playClick();
            addTaskPane.getChildren().clear();
            addTaskPane.getChildren().add(taskVBox);
            FadeTransition fade = new FadeTransition(Duration.millis(1000), taskVBox);
            fade.setFromValue(0.0);
            fade.setToValue(1.0);
            fade.play();
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("ENTER được nhấn");
                    String newTaskInput = newTaskField.getText();
                    String titleInput = titleFeild.getText();
                    if(Objects.equals(titleInput, "")) titleInput = "Untitle";
                    if(!Objects.equals(newTaskInput, ""))
                    {
                        MainController.addNewTask(newTaskInput, titleInput, username);
                    }
                    titleFeild.clear();
                    newTaskField.clear();
                    root.getChildren().clear();
                    addTaskPane.getChildren().clear();
                    addTaskPane.getChildren().add(addTaskButton);
                    root.getChildren().addAll(addTaskPane, signOutButton, showAllTaskOfUser(username));
                }
            });

            root.setOnMousePressed(_ -> addTaskPane.getChildren().setAll(addTaskButton));
        });


        return scene;
    }

    public static VBox showAllTaskOfUser(String username)
    {
        //load các tasks trong db của user
        VBox root = new VBox(2);
        List<Task> list;
        list = MainController.showAllTask(username);
        for(int i = 0; i < list.size() - 1; i++)
        {
            Task temp = list.get(i);

// Title
            Text titleText = new Text(temp.getTitle());
            titleText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            titleText.setFill(Color.GREEN);

// Description
            Text descriptionText = new Text(temp.getDescription());
            descriptionText.setFill(Color.WHITE);
            descriptionText.setWrappingWidth(300);

// Checkbox
            CheckBox doneButton = new CheckBox();
            doneButton.setSelected(temp.isDone());
            doneButton.setOnAction(_ -> {
                SoundController.playTick();
                MainController.tickDoneTask(temp);
                temp.setDone(!temp.isDone());
            });

// Title + Checkbox
            HBox titleBox = new HBox();
            titleBox.getChildren().addAll(titleText, new Region(), doneButton);
            HBox.setHgrow(titleBox.getChildren().get(1), Priority.ALWAYS);


// VBox chứa text
            VBox textVBox = new VBox(5, titleBox, descriptionText);
            textVBox.getStyleClass().add("task-box");
            HBox.setHgrow(textVBox, Priority.ALWAYS);

// Remove Button
            Button removeTaskButton = new Button("X");
            removeTaskButton.getStyleClass().add("remove-btn");
            removeTaskButton.setPrefWidth(40);
            removeTaskButton.setMaxHeight(Double.MAX_VALUE);

// Animation expand width
            removeTaskButton.setOnMouseEntered(_ -> {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300),
                        new KeyValue(removeTaskButton.prefWidthProperty(), 75)));
                timeline.play();
            });
            removeTaskButton.setOnMouseExited(_ -> {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300),
                        new KeyValue(removeTaskButton.prefWidthProperty(), 40)));
                timeline.play();
            });

// Wrapper cho nút
            VBox buttonWrapper = new VBox(removeTaskButton);
            buttonWrapper.setAlignment(Pos.CENTER);
            buttonWrapper.setMaxHeight(Double.MAX_VALUE);

// Task container
            HBox taskHBox = new HBox(textVBox, buttonWrapper);
            taskHBox.setAlignment(Pos.CENTER_LEFT);

// Remove action
            removeTaskButton.setOnAction(_ -> {
                root.getChildren().remove(taskHBox);
                MainController.removeTask(temp);
            });

// Add vào root
            root.getChildren().add(taskHBox);

        }
        return root;
    }
    public static Scene getSignUpScene(Stage stage)
    {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(Objects.requireNonNull(View.class.getResource("/style.css")).toExternalForm());

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

        goBackButton.setOnAction(_ -> MainController.signInUpController(stage, true));

        signUpButton.setOnAction(_ ->{
            String userNameInput = usernameFeild.getText();
            String userPassWord = passWordFeild.getText();
            String userPassWordRetype = passWordRetypeFeild.getText();
            if(passwordRetypeVBox.getChildren().getLast() instanceof Label)
            {
                passwordRetypeVBox.getChildren().removeLast();
            }
            if(userPassWordRetype.equals(userPassWord))
            {
                Node lastNode = root.getChildren().getLast();
                if(lastNode instanceof Label)
                {
                    root.getChildren().removeLast();
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
