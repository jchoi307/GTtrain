import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Joon on 2016-04-14.
 */
public class DBConTest {
    public static void main(String[] args){
        String DRIVER = "com.mysql.jdbc.Driver";
        String URL = "jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Team_84";
        String USER = "cs4400_Team_84";
        String PASS = "PZWSZLsT";
        JLabel msg = new JLabel();
        JFrame frame = new JFrame();
        Connection con = null;
        try {
            Class.forName(DRIVER).newInstance();
            con = DriverManager.getConnection(URL, USER, PASS);
            if (!con.isClosed())
                System.out.println("Successfully connected to " +
                        "MySQL server using TCP/IP...");
            msg.setText("That username is already taken. Please try again.");
            frame.getContentPane().add(BorderLayout.NORTH, msg);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {}
        }
    }
}
