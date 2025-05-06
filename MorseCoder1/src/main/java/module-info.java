module com.example.morsecoder1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.morsecoder1 to javafx.fxml;
    exports com.example.morsecoder1;
}