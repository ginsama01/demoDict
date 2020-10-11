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

    public DictionaryManagement() {

    }

    /**
     * thêm từ mới vào cây trie.
     * @param english từ tiếng anh
     * @param vietnamese nghĩa tiếng việt.
     */
    public void addNewWord(String english, String vietnamese )
    {
        int w;
        int idx = 0;
        for (int i = 0; i < english.length(); i++)
        {
            tree.get(idx).num++;
            if (english.charAt(i) == ' ') w = 26;
            else if (english.charAt(i) == '\'') w = 27;
            else if (english.charAt(i) == '-') w = 28;
            else if (english.charAt(i) == '.') w = 29;
            else w = english.charAt(i) - 'a';
            if (tree.get(idx).child[w] == -1)
            {
                New_node();
                tree.get(idx).child[w] = tree.size() - 1;
            }
            idx = tree.get(idx).child[w];
        }
        tree.get(idx).num++;
        tree.get(idx).Word_explain = vietnamese;
        tree.get(idx).endword++;
    }

    /**
     * sửa từ trong cây trie.
     * @param english từ tiếng anh
     * @param vietnamese nghĩa tiếng việt mới.
     */
    public void resetWord(String english, String vietnamese) {
        int n = c_pref(english);
        tree.get(n).Word_explain = vietnamese;
    }

    /**
     * xóa từ khỏi cây trie.
     * @param english từ tiếng anh cần xóa
     */
    public void deleteWord(String english) {
        if (c_pref(english) == 0) return;
        int w;
        int idx = 0;
        for (int i = 0; i < english.length(); i++)
        {
            tree.get(idx).num--;
            if (english.charAt(i) == ' ') w = 26;
            else if (english.charAt(i) == '\'') w = 27;
            else if (english.charAt(i) == '-') w = 28;
            else if (english.charAt(i) == '.') w = 29;
            else w = english.charAt(i)- 'a';
            idx = tree.get(idx).child[w];
        }
        tree.get(idx).num--;
        tree.get(idx).Word_explain = "";
        tree.get(idx).endword--;
    }

    /**
     * lấy danh sách từ điển = dòng lệnh. // không dùng.
     */
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

    /**
     * lấy danh sách từ điển = file txt // không dùng.
     */
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

    /**
     * lấy dữ liệu từ điển từ file db bằng SQLite, sử dụng thư viện JDBC.
     */
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
                // Lọc dữ liệu
                int ok = 1;
                for (int i = 0; i < s1.length(); ++i) {
                    if (s1.charAt(i) - 'a' >= 0 && s1.charAt(i) - 'a' <=25) continue;
                    if (s1.charAt(i) == ' ') continue;
                    if (s1.charAt(i) == '\'') continue;
                    if (s1.charAt(i) == '-') continue;
                    if (s1.charAt(i) == '.') continue;
                    ok = 0;
                    break;
                }
                if (ok == 0) continue;
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

    /**
     * thêm từ mới vào file db.
     * @param english từ tiếng anh
     * @param vietnamese nghĩa tiếng việt (dưới dạng text html)
     */
    public void addToDatabase(String english, String vietnamese) {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:dict_hh.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String query = "SELECT MAX(id) AS LAST FROM av";
            ResultSet rs = stmt.executeQuery(query);
            String maxID = rs.getString("LAST");
            int id = (Integer.parseInt(maxID)) + 1;
            String sql = "INSERT INTO av (id,word,html,description,pronounce) " +
                    "VALUES (" + id + ", '" + english + "', '" + vietnamese + "', 'a', 'a' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }

    /**
     * sửa từ đã có trong file db
     * @param english từ tiếng anh
     * @param vietnamese nghĩa tiếng việt mới (dưới dạng text html)
     */
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

    /**
     * xóa từ khỏi file db.
     * @param english từ cần xóa
     */
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

    /**
     * tra cứu một từ từ cây trie.
     * @param englishWord từ tiếng anh cần tra cứu
     * @return nghĩa tiếng việt dưới dạng text/html
     */
    public String dictionaryLookup(String englishWord) {
        int n = c_pref(englishWord);
        if (tree.get(n).Word_explain.isEmpty()) {
            return "Can't find this word";
        }
        else {
            return tree.get(n).Word_explain;
        }
    }

    /**
     * method của commandline theo đề bài nhưng không dùng được đưa vào commend ẩn.
     */
    /*
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
    }*/


}
