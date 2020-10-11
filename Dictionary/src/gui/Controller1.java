package gui;

import dbforbook.DatabaseForBook;
import freettsspeech.FreettsSpeech;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import commandline.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Optional;

//Controller scene1
public class Controller1 {

    private String wordSpeaker; // từ phát âm

    @FXML
    ObservableList<String> list = FXCollections.observableArrayList(); //list quản lí listview

    @FXML
    private ListView<String> listView; //danh sách từ gợi ý

    @FXML
    private TextField textField; //text nhập từ cần tra

    @FXML
    private WebView webView; //hiện text định dạng web (html)
    private WebEngine engine; //quản lí dữ liệu do webView

    @FXML
    private ImageView speaker; //biểu tượng phát âm

    @FXML
    private ImageView addImage; // biểu tượng thêm từ mới vào BOOK

    /**
     * bắt sự kiện chuột ấn vào cho listview các từ gợi ý.
     * @param event sự kiện chuột
     */
    @FXML
    public void listviewMouseAction(MouseEvent event) {
        String wordSelect = listView.getSelectionModel().getSelectedItem();
        wordSpeaker = wordSelect;
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
        speaker.setVisible(true);
        addImage.setVisible(true);
    }

    /**
     * bắt sự kiện On Action cho Text Field nhập từ cần tra.
     * @param event sự kiện action
     */
    @FXML
    public void searchOnAction(ActionEvent event) {
        String wordSelect = textField.getText();
        wordSelect = wordSelect.toLowerCase();
        wordSpeaker = wordSelect;
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
        speaker.setVisible(true);
        addImage.setVisible(true);
    }

    /**
     * Bắt sự kiện Mouse Clicked cho biểu tượng kính lúp, tra từ.
     * @param event sự kiện chuột
     */
    @FXML
    public void clickOnLookup(MouseEvent event) {
        String wordSelect = textField.getText();
        wordSelect = wordSelect.toLowerCase();
        wordSpeaker = wordSelect;
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
    }

    /**
     * Bắt sự kiện Mouse Clicked cho biểu tượng phát âm -> phát âm từ.
     * @param event sự kiện chuột
     */
    @FXML
    public void clickOnSpeaker(MouseEvent event) {
        FreettsSpeech.speech(wordSpeaker);
    }

    /**
     * Bắt sự kiện Mouse Clicked cho biểu tượng add BOOK -> thêm từ mới vào BOOK.
     * @param event sự kiện chuột
     */
    @FXML
    public void clickOnAddImage(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add to BOOK");
        dialog.setHeaderText("Enter your book");
        dialog.setContentText("Name of BOOK");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(bookName ->{
            DatabaseForBook.addWordtoBook(bookName, wordSpeaker);
        });
    }

    /**
     * Cập nhật lại danh sách từ gợi ý.
     * @param words từ đang nhập
     */
    public void loadList(String words) {
        list.clear();
        words = words.toLowerCase();
        words = Main.testing.dictionarySearcher(words);
        if (words.isEmpty()) return;
        String[] ss = words.split(",");
        for (int i = 0; i < ss.length; ++i) {
            list.add(ss[i]);
        }
    }

    /**
     * Đặt speaker, addImage ẩn khi bắt đầu chạy, merge các dữ liệu liên quan, bắt sự kiện
     * text thay đổi, add tooltip (text ẩn) cho các image
     */
    @FXML
    public void initialize() {
        engine = webView.getEngine();
        listView.setItems(list);
        speaker.setVisible(false);
        addImage.setVisible(false);
        //Bắt sự kiện text thay đổi cho textField.
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                loadList(newValue);
            } else {
                list.clear();
            }
        });
        final Tooltip tooltip1 = new Tooltip("Speaker");
        speaker.setPickOnBounds(true);
        Tooltip.install(speaker, tooltip1);
        final Tooltip tooltip2 = new Tooltip("Add this word to BOOK");
        addImage.setPickOnBounds(true);
        Tooltip.install(addImage, tooltip2);
    }


}
