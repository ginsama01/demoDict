package commandline;
import gui.*;

public class DictionaryCommandline extends DictionaryManagement{

    private int getLengthNumber(int i) {
        int count = 0;
        if (i == 0) return 1;
        while (i > 0) {
            ++count;
            i /= 10;
        }
        return count;
    }

    public void showAllWords() {
        System.out.println("No    | English           | Vietnamese");
        for (int i = 0; i < words.size(); ++i) {
            System.out.print(i);
            for (int j = 0; j < 6 - getLengthNumber(i); ++j) {
                System.out.print(" ");
            }
            System.out.print("| " + words.get(i).getWord_target());
            for (int j = 0; j < 18 - words.get(i).getWord_target().length(); ++j) {
                System.out.print(" ");
            }
            System.out.print("| ");
            System.out.print(words.get(i).getWord_explain() + "\n");
        }
    }

    public String dictionarySearcher(String startLookup) {
        String s = "";
        for (int i = 0; i < words.size(); ++i) {
            if (words.get(i).getWord_target().startsWith(startLookup)) {
                s = s + words.get(i).getWord_target() + ",";
            }
        }
        return s;
    }


}