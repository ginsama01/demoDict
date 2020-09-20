import java.io.File;

public class Main {
    public static void main(String[] args) {
        DictionaryCommandline testing = new DictionaryCommandline();
        testing.dictionaryAdvanced("woman");
        testing.dictionarySearcher("a");
        testing.dictionaryExportToFile();
    }
}
