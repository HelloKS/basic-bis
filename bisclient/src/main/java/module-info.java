module kumoh.basicbis {
    requires javafx.controls;
    requires javafx.fxml;
//    requires jfxrt;
//    requires rt;

    opens kumoh.basicbis to javafx.fxml;
    exports kumoh.basicbis;
}
