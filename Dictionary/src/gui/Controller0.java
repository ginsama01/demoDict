package gui;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Controller0 {
    @FXML
    private WebView webView;
    private WebEngine webEngine;

    private String text = "<br>\n" +
            "<h1> Từ điển Anh - Việt </h1>\n" +
            "<h3><i>Made by Huyền Thanh Nguyễn(19020048) and Quang Huy Nguyễn(19020013) </i></h3>\n" +
            "\n" +
            "<br>\n" +
            "\n" +
            "<ul>\n" +
            "\n" +
            "\t<li>  Lập trình bằng Java, sử dụng JavaFX làm giao diện đồ họa, sử dụng API Translator và API Speech của Azure, sử dụng database public trên mạng có gần 110000 từ. </li>\n" +
            "\t<li> Project sử dụng jdk 1.8, viết bằng IntelliJ IDEA Ultimate.</li>\n" +
            "\t<li> Dictionary: </li>\n" +
            "\t\t<ul>\n" +
            "\t    <li> Tra từ tiếng Anh sang tiếng Việt qua thanh tìm kiếm, phát âm khi ấn vào biểu tượng loa.</li>\n" +
            "\t</ul>\n" +
            "\n" +
            "\t <li>Edit/Remove:</li>\n" +
            "\t <ul>\n" +
            "        <li>Add/Edit : Khi nhập 1 từ tiếng Anh - tiếng Việt, nếu từ tiếng Anh đã có trong từ điển thì thay nghĩa của từ đó trong từ điển bằng nghĩa mới, nếu chưa có thì thêm từ đó vào từ điển qua button Add/Edit. </li>\n" +
            "        <li>Remove : Nhập 1 từ tiếng Anh, xóa từ đó khỏi từ điển qua button Remove</li>\n" +
            "    </ul>\n" +
            "\n" +
            "    <li>Translate: </li> \n" +
            "    <ul>\n" +
            "\n" +
            "        <li>Dịch đoạn văn Anh - Việt, Việt - Anh, tích hợp phát âm bằng API Azure. </li>\n" +
            "        <li>API có limit resquest, đề nghị không lạm dụng.</li>\n" +
            "    </ul>\n" +
            "    <li>Book: </li>\n" +
            "    <ul>\n" +
            "        <li>Sổ tay ghi chú : giúp người dùng ghi lại những từ quan trọng theo từng chủ đề khác nhau.</li>\n" +
            "        <li>Thêm từ bằng cách ấn vào dấu + khi sử dụng Dictionary. </li>\n" +
            "        <li>Tạo mới Book bằng cách ấn vào dấu + trong tab BOOK. </li>\n" +
            "    </ul>\n" +
            "</ul>\n";
    @FXML
    public void initialize() {
        webEngine = webView.getEngine();
        webEngine.loadContent(text, "text/html");
    }
}
