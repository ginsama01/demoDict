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


public class Controller1 {

    private String wordSpeaker;

    @FXML
    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField textField;

    @FXML
    private WebView webView;
    private WebEngine engine;

    @FXML
    private ImageView speaker;

    @FXML
    private ImageView addImage;

    @FXML
    public void listviewMouseAction(MouseEvent event) {
        String wordSelect = listView.getSelectionModel().getSelectedItem();
        wordSpeaker = wordSelect;
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
        speaker.setVisible(true);
        addImage.setVisible(true);
    }

    @FXML
    public void searchOnAction(ActionEvent event) {
        String wordSelect = textField.getText();
        wordSpeaker = wordSelect;
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
        speaker.setVisible(true);
        addImage.setVisible(true);
    }

    @FXML
    public void clickOnLookup(MouseEvent event) {
        String wordSelect = textField.getText();
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
    }

    @FXML
    public void clickOnSpeaker(MouseEvent event) {
        FreettsSpeech.speech(wordSpeaker);
    }

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

    public void loadList(String words) {
        list.clear();
        words = Main.testing.dictionarySearcher(words);
        if (words.isEmpty()) return;
        String[] ss = words.split(",");
        for (int i = 0; i < ss.length; ++i) {
            list.add(ss[i]);
        }
    }

    @FXML
    public void initialize() {
        engine = webView.getEngine();
        listView.setItems(list);
        speaker.setVisible(false);
        addImage.setVisible(false);
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
