import java.util.Scanner;

public class DictionaryManagement extends Dictionary {

    public void addNewWord(String english, String vietnamese) {
        Word newWord = new Word(english, vietnamese);
        words.add(newWord);
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


}
