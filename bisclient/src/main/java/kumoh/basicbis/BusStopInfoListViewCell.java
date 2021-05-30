//package kumoh.basicbis;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListCell;
//import javafx.scene.layout.AnchorPane;
//import kumoh.basicbis.persistence.BusStopInfo;
//
//import java.io.IOException;
//
//public class BusStopInfoListViewCell extends ListCell<BusStopInfo> {
//    @FXML private Label rtLabel;
//    @FXML private AnchorPane rtAnchorPane;
//    public BusStopInfoListViewCell() {
//        try{
//            FXMLLoader loader = new FXMLLoader();
//            loader.setController(this);
//            loader.setLocation(getClass().getResource("ListCell.fxml"));
//            loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException("Creating UI component failed", e);
//        }
//    }
//    @Override
//    protected void updateItem(BusStopInfo bus, boolean empty){
//        super.updateItem(bus, empty);
//        if(empty || bus == null){
//            setText(null);
//            setGraphic(null);
//        } else {
//            String[] str = bus.toString().split(",");
//            String name = str[2];
//            rtLabel.setText(name);
//            setGraphic(rtAnchorPane);
//        }
//    }
//}
