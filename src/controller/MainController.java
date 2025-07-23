package controller;

import dataLink.DBconnect;
import javafx.stage.Stage;
import model.Task;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    public static boolean loginController(Stage stage, String username, String password) {
        DBconnect dbconnect = new DBconnect();
        boolean success = dbconnect.checkUserSignIn(username, password);

        if (success) {
            System.out.println("Đăng nhập thành công!");
            stage.setScene(View.getMainView(username));
            return true;
        } else {
            System.out.println("Sai tài khoản hoặc mật khẩu!");
            return false;
            // Có thể hiển thị cảnh báo cho người dùng ở đây
        }
    }

    public static void signInUpController(Stage stage, boolean choose)
    {
        if(choose)
        {
            stage.setScene(View.getLoginView(stage));
        }
        else{
            stage.setScene(View.getSignUpScene(stage));
        }
    }

    public static boolean signUpController(String username, String password)
    {
        DBconnect dBconnect = new DBconnect();
        if(dBconnect.checkUserSignUp(username, password))
        {
            System.out.println("Đăng ký thành công!!!");
            return true;
        }
        else{
            System.out.println("Dăng ký thất bại...");
            return false;
        }
    }

    public static void addNewTask(String description, String title, String username)
    {
        DBconnect.addNewTask(description, title, username);
    }

    public static List<Task> showAllTask(String username)
    {
        List<Task> res = new ArrayList<>();
        res = DBconnect.getAllTaskData(username);
        return res;
    }

    public static void removeTask(Task removedTask)
    {
        DBconnect.removeTaskFromDB(removedTask);
    }

    public static void tickDoneTask(Task doneTask)
    {
        DBconnect.tickDoneTaskInDB(doneTask);
    }
}
