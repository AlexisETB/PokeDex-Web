module ec.edu.uce.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens ec.edu.uce.demo to javafx.fxml;
    exports ec.edu.uce.demo;
}