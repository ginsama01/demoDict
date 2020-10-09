package commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Formatter;
import java.util.Scanner;
import gui.*;

public class DictionaryManagement extends Dictionary {

    public void addNewWord(String english, String vietnamese) {
        Word newWord = new Word(english, vietnamese);
        words.add(newWord);
    }

    public void resetWord(String english, String vietnamese) {
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getWord_target().equals(english)) {
                words.get(i).setWord_explain(vietnamese);
            }
        }
    }

    public void deleteWord(String english) {
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getWord_target().equals(english)) {
                words.remove(i);
            }
        }
    }

    public void insertFromCommandline() {
        int nWords;
        Scanner sc = new Scanner(System.in);
        nWords = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < nWords; ++i) {
            String english = sc.nextLine();
            String vietnamese = sc.nextLine();
            addNewWord(english, vietnamese);
        }
    }

    public void insertFromFile() {
        try {
            Scanner sc = new Scanner(new File("dictionaries.txt"));
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                String[] ss = s.split("\\t");
                addNewWord(ss[0], ss[1]);
            }
            sc.close();
        } catch (FileNotFoundException e1) {
            return;
        }
    }

    public void insertFromDatabase() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM av;");
            while (result.next()) {
                String s1 = result.getString("word");
                String s2 = result.getString("html");
                addNewWord(s1, s2);
            }
            result.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void addToDatabase(String english, String vietnamese) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO av (id,word,html,description,pronounce) " +
                    "VALUES (" + Main.testing.getWordsSize() + ", '" + english + "', '" + vietnamese + "', 'a', 'a' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    public void updateDatabase(String english, String vietnamese) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "UPDATE av SET html = '" + vietnamese +"' where word LIKE '" + english + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

    public void removeFromDatabase(String english) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "DELETE FROM av where word LIKE '" + english + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("Failed");
        }
    }

    public String dictionaryLookup(String englishWord) {
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getWord_target().equals(englishWord)) {
                return words.get(i).getWord_explain();
            }
        }
        return "Can't find this word";
    }

    public void dictionaryExportToFile() {
        try {
            Formatter f = new Formatter("out.txt");
            for (int i = 0; i < words.size(); ++i) {
                f.format("%s : %s%n", words.get(i).getWord_target(), words.get(i).getWord_explain());
            }
            f.close();
        } catch (FileNotFoundException e1) {
            return;
        }
    }
}
