module kumoh.basicbis {
    requires javafx.controls;
    requires javafx.fxml;

    opens kumoh.basicbis to javafx.fxml;
    exports kumoh.basicbis;
}
