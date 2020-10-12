package gui;

import dbforbook.DatabaseForBook;
import freettsspeech.FreettsSpeech;
import googletexttospeech.JavaGoogleTextToSpeech;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Optional;

//Controller scene4
public class Controller4 {

    private String wordSpeaker; //từ phát âm
    private String recentBookname; //tên book name hiện tại

    @FXML
    ObservableList<String> list = FXCollections.observableArrayList();
    //list quản lí ListView

    @FXML
    ObservableList<String> listChoice = FXCollections.observableArrayList();
    //list các BOOK

    @FXML
    private ListView<String> listView;
    //list các từ trong BOOK

    @FXML
    private ImageView speaker; //image phát âm

    @FXML
    private ImageView speaker1;

    @FXML
    private ImageView removeImage; //image remove word

    @FXML
    private ImageView addBook; //image add new book

    @FXML
    private ImageView refresh; //image reload book

    @FXML
    private WebView webView; //web view hiển thị text/html nghĩa
    private WebEngine engine; //quản lí webView

    @FXML
    private ChoiceBox choiceBox; //Box lựa chọn BOOK


    /**
     * Bắt sự kiện Mouse Clicked cho biểu tượng phát âm -> phát âm từ = source github.
     * @param event sự kiện chuột
     */
    @FXML
    public void clickOnSpeaker(MouseEvent event) {
        JavaGoogleTextToSpeech.speak(wordSpeaker);
    }

    /**
     * Bắt sự kiện Mouse Clicked cho biểu tượng phát âm -> phát âm từ = thư viện freetts.
     * @param event sự kiện chuột
     */
    @FXML
    public void clickOnSpeaker1(MouseEvent event)
    {
        FreettsSpeech.speech(wordSpeaker);
    }

    /**
     * Bắt sự kiện Mouse CLicked cho Image add Book -> Add new BOOK.
     * @param event sự kiện chuột
     */
    @FXML
    public void addBookAction(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add new BOOK");
        dialog.setHeaderText("Enter your new book");
        dialog.setContentText("Name of BOOK");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(bookName ->{
            DatabaseForBook.newBook(bookName);
            listChoice.add(bookName);
        });

    }

    /**
     * Bắt sự kiện Mouse Clicked cho Image, xóa từ khỏi book.
     * @param event sự kiện chuột.
     */
    @FXML
    public void clickOnRemove(MouseEvent event) {
        DatabaseForBook.removeWordFromBook(recentBookname, wordSpeaker);
        list.remove(wordSpeaker);
    }

    /**
     * Bắt sự kiện Mouse Clicked cho Image, reload lại BOOK.
     * @param event
     */
    @FXML
    public void clickOnRefresh(MouseEvent event) {
        list.clear();
        String s = DatabaseForBook.loadBook(recentBookname);
        if (!s.isEmpty()) {
            String[] ss = s.split(",");
            for (int i = 0; i < ss.length; ++i) {
                list.add(ss[i]);
            }
        }
    }

    /**
     * Load dữ liệu của Book.
     */
    public void loadList() {
        String s = DatabaseForBook.loadBookName();
        if (!s.isEmpty()) {
            String[] ss = s.split(",");
            listChoice.addAll(ss);
        }
    }

    /**
     * Set tooltip cho các Image.
     * Bắt sự kiện cho choiceBox.
     */
    @FXML
    public void initialize() {
        engine = webView.getEngine();
        loadList();
        listView.setItems(list);
        speaker.setVisible(false);
        removeImage.setVisible(false);
        speaker1.setVisible(false);

        final Tooltip tooltip1 = new Tooltip("Speaker");
        speaker.setPickOnBounds(true);
        Tooltip.install(speaker, tooltip1);
        speaker1.setPickOnBounds(true);
        Tooltip.install(speaker1, tooltip1);
        final Tooltip tooltip2 = new Tooltip("Remove this word from BOOK");
        removeImage.setPickOnBounds(true);
        Tooltip.install(removeImage, tooltip2);
        final Tooltip tooltip3 = new Tooltip("Refresh BOOK");
        refresh.setPickOnBounds(true);
        Tooltip.install(refresh, tooltip3);
        final Tooltip tooltip4 = new Tooltip("Create BOOK");
        addBook.setPickOnBounds(true);
        Tooltip.install(addBook, tooltip4);

        //Bắt sự kiện selected list BOOK thay đổi.
        choiceBox.setItems(listChoice);
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            list.clear();
            recentBookname = newValue.toString();
            String s = DatabaseForBook.loadBook(newValue.toString());
            if (!s.isEmpty()) {
                String[] ss = s.split(",");
                for (int i = 0; i < ss.length; ++i) {
                    list.add(ss[i]);
                }
            }
        });

        //Bắt sự kiện selected item change.
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String wordSelect = listView.getSelectionModel().getSelectedItem();
            if (!wordSelect.isEmpty()) {
                wordSpeaker = wordSelect;
                wordSelect = Main.testing.dictionaryLookup(wordSelect);
                engine.loadContent(wordSelect, "text/html");
                speaker.setVisible(true);
                removeImage.setVisible(true);
                speaker1.setVisible(true);
            }
        });
    }
}
