import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

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

    public void dictionaryLookup(String englishWord) {
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getWord_target().equals(englishWord)) {
                System.out.println(englishWord + " : " + words.get(i).getWord_explain());
                break;
            }
        }
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
