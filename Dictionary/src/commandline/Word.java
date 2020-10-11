package commandline;

/**
 * mot node cua cay trie.
 */
public class Word {
    protected int id;
    protected int num;
    protected int endword;
    protected int[] child = new int[30];
    protected String Word_explain = "";
}

/**
 * method của commandline theo đề bài nhưng không dùng được đưa vào commend ẩn.
 */
/*
public class Word {
    private String word_target;
    private String word_explain;

    public String getWord_target() {
        return word_target;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public Word() {
        word_explain = new String();
        word_explain = new String();
    }

    public Word(String word_target, String word_explain) {
        this.word_explain = word_explain;
        this.word_target = word_target;
    }

}*/
