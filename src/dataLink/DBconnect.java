package dataLink;

import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBconnect {
    public boolean checkUserSignIn(String username, String password)
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_app", "hoang", "123456");
            System.out.println("Kết nối thành công!");
            String sql = "SELECT * FROM users WHERE userName = ? and password = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet res = stmt.executeQuery();
            boolean userExists = res.next();

            res.close();
            stmt.close();
            conn.close();
            return userExists;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean checkUserSignUp(String username, String password)
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_app", "hoang", "123456");
            System.out.println("Kết nối thành công!");
            String sql = "INSERT INTO users VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();

            stmt.close();
            conn.close();
            return rowsAffected > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public static void addNewTask(String description, String title, String username)
    {
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_app", "hoang", "123456");
            System.out.println("Kết nối thành công!");
            String sql = "insert into TASK(title, description, done) values(?, ?, FALSE)";
            String sql1 = "insert into LINKED values(?, ?)";
            PreparedStatement stmt =  conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmt1 = conn.prepareStatement(sql1);
            stmt.setString(1, title);
            stmt.setString(2,  description);
            System.out.println("1");
            stmt.executeUpdate();
            System.out.println("2");
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            System.out.println("3");
            int taskId = -1;
            if (generatedKeys.next()) {
                System.out.println("4");
                taskId = generatedKeys.getInt(1);
            }
            System.out.println("5");
            stmt1.setString(1, username);
            stmt1.setInt(2, taskId);
            stmt1.executeUpdate();
            System.out.println("6");
            stmt.close();
            stmt1.close();
            conn.close();
        }
        catch (SQLException e){
            System.out.println("Lỗi không thể thêm task");
        }
    }

    public static List<Task> getAllTaskData(String username)
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_app", "hoang", "123456");
            System.out.println("Kết nối thành công!");
            String sql = new String("select title, description\n" +
                    "from TASK \n" +
                    "join LINKED on LINKED.idTask = TASK.id\n" +
                    "where LINKED.username = ?");

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet output = stmt.executeQuery();

            List<Task> res = new ArrayList<>();
            while(output.next())
            {
                res.add(new Task(output.getString(1), output.getString(2)));
            }

            conn.close();
            stmt.close();
            output.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
