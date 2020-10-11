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

//Controller scene3
public class Controller3 {
    @FXML
    private TextArea textArea1; //text cần tra

    @FXML
    private TextArea textArea2; //text nghĩa

    @FXML
    ObservableList<String> list1 = FXCollections.observableArrayList("English", "Vietnamese");
    //list quản lí choiceBox1;

    @FXML
    ObservableList<String> list2 = FXCollections.observableArrayList("Vietnamese", "English");
    //list quản lí choiceBox2;

    @FXML
    ChoiceBox choiceBox1; //Lựa chọn ngôn ngữ của text cần tra.

    @FXML
    ChoiceBox choiceBox2; //Lựa chọn ngôn ngữ của text dịch nghĩa.

    @FXML
    ImageView imageTrans; //image dịch

    /**
     * Bắt sự kiện Mouse Clicked của Image có chức năng dịch đoạn văn (gọi method gửi
     * API đến sever Aruze Microsoft)
     * @param event sự kiện chuột
     */
    @FXML
    public void transClickEvent(MouseEvent event) {
        String text = textArea1.getText();
        text = AzureTranslate.Translate(text);
        textArea2.setText(text);
    }

    /**
     * Bắt sự kiện Mouse Clicked cho Image phát âm 1, có chức năng phát âm đoạn văn cần dịch
     * (gửi API đến sever Aruze Microsoft)
     * @param event sự kiện chuột
     */
    @FXML
    public void speechTextTrans(MouseEvent event) {
        String text = textArea1.getText();
        if (choiceBox1.getValue().equals("English")) {
            AzureSpeech.textToSpeech("en-GB", "en-GB-Susan", text);
        } else if (choiceBox1.getValue().equals("Vietnamese")) {
            AzureSpeech.textToSpeech("vi-VN", "vi-VN-An", text);
        }
    }

    /**
     * Bắt sự kiện Mouse Clicked cho Image phát âm 2, có chức năng phát âm đoạn văn dịch nghĩa
     * (gửi API đến sever Aruze Microsoft)
     * @param event sự kiện chuột
     */
    @FXML
    public void speechTextDefinition(MouseEvent event) {
        String text = textArea2.getText();
        if (choiceBox2.getValue().equals("English")) {
            AzureSpeech.textToSpeech("en-GB", "en-GB-Susan", text);
        } else if (choiceBox2.getValue().equals("Vietnamese")) {
            AzureSpeech.textToSpeech("vi-VN", "vi-VN-An", text);
        }
    }

    /**
     * Set khung text cho 2 text Area, cấm ghi cho textArea dịch, bắt sự kiện Choice Box
     * thay đổi Selection
     */
    @FXML
    public void initialize() {
        textArea1.setWrapText(true);
        choiceBox1.getItems().addAll(list1);
        choiceBox2.getItems().addAll(list2);
        textArea2.setEditable(false);
        //Bắt sự kiện thay đổi ngôn ngữ dịch
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
