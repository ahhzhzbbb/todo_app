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
            String sql = "SELECT * FROM USERS WHERE userName = ? and password = ?";

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
            String sql = "INSERT INTO USERS VALUES (?, ?)";
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
            stmt.setString(2, description);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int taskId = -1;
            if (generatedKeys.next()) {
                taskId = generatedKeys.getInt(1);
            }
            stmt1.setString(1, username);
            stmt1.setInt(2, taskId);
            stmt1.executeUpdate();
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
            String sql = """
                    select id, title, description, done
                    from TASK\s
                    join LINKED on LINKED.idTask = TASK.id
                    where LINKED.username = ?
                    ORDER BY time DESC""";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet output = stmt.executeQuery();

            List<Task> res = new ArrayList<>();
            while(output.next())
            {
                res.add(new Task(output.getInt("id") , output.getString("description"), output.getString("title"), output.getBoolean("done")));
            }

            conn.close();
            stmt.close();
            output.close();
            return res;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeTaskFromDB(Task removedTask) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_app", "hoang", "123456");
            System.out.println("Kết nối thành công!");

            PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM linked WHERE idTask = ?");
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM task WHERE id = ?");
            int id = removedTask.getId();
            stmt1.setInt(1, id);
            stmt2.setInt(1, id);

            stmt1.executeUpdate(); // đổi từ execute() -> executeUpdate()
            stmt2.executeUpdate();

            stmt1.close();
            stmt2.close();
            conn.close();
            System.out.println("Đã xoá thành công task trong DataBase!!!");
            System.out.println("ID xoá: " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void tickDoneTaskInDB(Task task)
    {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo_app", "hoang", "123456");
            System.out.println("Kết nối thành công!");

            PreparedStatement stmt1 = conn.prepareStatement("""
                    UPDATE task
                    set done = not done
                    where id = ?""");
            stmt1.setInt(1, task.getId());
            stmt1.executeUpdate();

            stmt1.close();
            conn.close();
            System.out.println("đã tích thành công trong DB");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
