module com.jae.connect4minmax {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jae.connect4minmax to javafx.fxml;
    exports com.jae.connect4minmax;
}