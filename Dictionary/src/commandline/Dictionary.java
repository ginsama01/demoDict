package commandline;

import java.util.ArrayList;

/**
 * cây trie.
 */
public class Dictionary {

    protected ArrayList <Word> tree = new ArrayList<Word>();
    public String t;
    public String s;

    /**
     * tạo một node mới.
     */
    public void New_node()
    {
        Word root = new Word();
        root.num = 0;
        for (int i = 0; i < 30; i++) root.child[i] = -1;
        root.id = tree.size();
        tree.add(root);
    }

    public Dictionary()
    {
        New_node();
    }

    /**
     * @param english từ tiếng anh cần tìm.
     * @return id trong cây trie.
     */
    public int c_pref(String english)
    {
        int w;
        int idx = 0;
        for (int i = 0; i < english.length(); i++)
        {
            if (english.charAt(i) == ' ') w = 26;
            else if (english.charAt(i) == '\'') w = 27;
            else if (english.charAt(i) == '-') w = 28;
            else if (english.charAt(i) == '.') w = 29;
            else w = english.charAt(i) - 'a';
            if (tree.get(idx).child[w] == -1) return 0;
            idx = tree.get(idx).child[w];
        }
        return idx;
    }



}

/**
 * method của commandline theo đề bài nhưng không dùng được đưa vào commend ẩn.
 */
/*
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    protected ArrayList<Word> words = new ArrayList<Word>();

    public int getWordsSize() {
        return words.size();
    }


}*/
