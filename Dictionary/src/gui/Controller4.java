package gui;

import dbforbook.DatabaseForBook;
import freettsspeech.FreettsSpeech;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Optional;

public class Controller4 {

    private String wordSpeaker;
    private String recentBookname;

    @FXML
    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    ObservableList<String> listChoice = FXCollections.observableArrayList();

    @FXML
    private ListView<String> listView;

    @FXML
    ImageView speaker;

    @FXML
    ImageView removeImage;

    @FXML
    ImageView addBook;

    @FXML
    private WebView webView;
    private WebEngine engine;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    public void listviewMouseAction(MouseEvent event) {
        String wordSelect = listView.getSelectionModel().getSelectedItem();
        wordSpeaker = wordSelect;
        wordSelect = Main.testing.dictionaryLookup(wordSelect);
        engine.loadContent(wordSelect, "text/html");
        speaker.setVisible(true);
        removeImage.setVisible(true);
    }

    @FXML
    public void clickOnSpeaker(MouseEvent event) {
        FreettsSpeech.speech(wordSpeaker);
    }

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

    @FXML
    public void clickOnRemove(MouseEvent event) {
        DatabaseForBook.removeWordFromBook(recentBookname, wordSpeaker);
        list.remove(wordSpeaker);
    }

    public void loadList() {
        String s = DatabaseForBook.loadBookName();
        if (!s.isEmpty()) {
            String[] ss = s.split(",");
            listChoice.addAll(ss);
        }
    }
    @FXML
    public void initialize() {
        engine = webView.getEngine();
        loadList();
        listView.setItems(list);
        speaker.setVisible(false);
        removeImage.setVisible(false);
        final Tooltip tooltip1 = new Tooltip("Speaker");
        speaker.setPickOnBounds(true);
        Tooltip.install(speaker, tooltip1);
        final Tooltip tooltip2 = new Tooltip("Remove this word from BOOK");
        removeImage.setPickOnBounds(true);
        Tooltip.install(removeImage, tooltip2);
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
    }
}
