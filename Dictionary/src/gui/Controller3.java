package gui;

import azureapi.AzureSpeech;
import azureapi.AzureTranslate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class Controller3 {
    @FXML
    private TextArea textArea1;

    @FXML
    private TextArea textArea2;

    @FXML
    ObservableList<String> list1 = FXCollections.observableArrayList("English", "Vietnamese");

    @FXML
    ObservableList<String> list2 = FXCollections.observableArrayList("Vietnamese", "English");

    @FXML
    ChoiceBox choiceBox1;

    @FXML
    ChoiceBox choiceBox2;

    @FXML
    ImageView imageTrans;

    @FXML
    public void transClickEvent(MouseEvent event) {
        String text = textArea1.getText();
        text = AzureTranslate.Translate(text);
        textArea2.setText(text);
    }

    @FXML
    public void speechTextTrans(MouseEvent event) {
        String text = textArea1.getText();
        if (choiceBox1.getValue().equals("English")) {
            AzureSpeech.textToSpeech("en-GB", "en-GB-Susan", text);
        } else if (choiceBox1.getValue().equals("Vietnamese")) {
            AzureSpeech.textToSpeech("vi-VN", "vi-VN-An", text);
        }
    }

    @FXML
    public void speechTextDefinition(MouseEvent event) {
        String text = textArea2.getText();
        if (choiceBox2.getValue().equals("English")) {
            AzureSpeech.textToSpeech("en-GB", "en-GB-Susan", text);
        } else if (choiceBox2.getValue().equals("Vietnamese")) {
            AzureSpeech.textToSpeech("vi-VN", "vi-VN-An", text);
        }
    }

    @FXML
    public void initialize() {
        textArea1.setWrapText(true);
        choiceBox1.getItems().addAll(list1);
        choiceBox2.getItems().addAll(list2);
        textArea2.setEditable(false);
        choiceBox2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("English")) {
                AzureTranslate.setUrl("en");
            } else if (newValue.equals("Vietnamese")) {
                AzureTranslate.setUrl("vi");
            }
        });
        textArea2.setWrapText(true);
    }
}
