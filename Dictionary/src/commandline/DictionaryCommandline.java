package commandline;
import gui.*;

public class DictionaryCommandline extends DictionaryManagement{

    public DictionaryCommandline() {

    }

    /**
     * search những từ bắt đầu bằng startLookup.
     * @param startLookup đoạn đầu từ
     * @return những từ bắt đầu bằng startLookup, phân cách bởi dấu phẩy ","
     */
    public String dictionarySearcher(String startLookup) {
        s = "";
        t = startLookup;
        int idx = c_pref(startLookup);
        if(idx != 0) {
            if (!tree.get(idx).Word_explain.isEmpty()) {
                s = s + startLookup + ",";
            }
            dfs(idx);
        }
        return s;
    }

    /**
     * method của commandline theo đề bài nhưng không dùng được đưa vào commend ẩn.
     */
    /*
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
    }*/


}
