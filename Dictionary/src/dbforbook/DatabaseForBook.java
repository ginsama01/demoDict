package dbforbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseForBook {

    public static void newBook(String bookName) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:book1.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "CREATE TABLE " + bookName + " " +
                    "(id INT PRIMARY KEY     NOT NULL," +
                    " word           TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

    public static void addWordtoBook(String bookName, String word) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:book1.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "SELECT * FROM " + bookName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            int n = 0;
            while (rs.next()) {
                ++n;
            }
            sql = "INSERT INTO " + bookName + " (id,word) VALUES ("
                    + n + ", '" + word + "' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

    public static void removeWordFromBook(String bookName, String word) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:book1.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE FROM " + bookName +
                    " where word LIKE '" + word + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }

    }

    public static String loadBook(String bookName) {
        Connection c = null;
        Statement stmt = null;
        String s = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:book1.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "SELECT * FROM " + bookName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                s = s + rs.getString("word") + ",";
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
        return s;
    }

    public static String loadBookName() {
        String s = "";
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:book1.db");
            ResultSet rs = c.getMetaData().getTables(null, null, null, null);
            while (rs.next()) {
                s = s + rs.getString("TABLE_NAME") + ",";
            }
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
        return s;
    }

}
