package kumoh.basicbis;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import kumoh.basicbis.persistence.BusInfo;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.util.BusStopTask;
import kumoh.basicbis.util.ImageLoadTask;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewBusStopInfoController implements Initializable {

    @FXML ListView<BusStopInfo> stopList;
    @FXML TextField searchField;
    @FXML Button showRoute;
    @FXML Button showFood;
    @FXML Button showComingBus;
    @FXML ImageView mapImage;
    @FXML Label stopName;

    private ObservableList<BusStopInfo> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 리스트뷰 표시 Cell factory
        stopList.setCellFactory(busStopInfoListView -> new BusStopInfoListViewCell());

        // 리스트 클릭 안되면 버튼 비활성화하게끔 지정
        showRoute.disableProperty().bind(stopList.getSelectionModel().selectedItemProperty().isNull());
        showFood.disableProperty().bind(stopList.getSelectionModel().selectedItemProperty().isNull());
        showComingBus.disableProperty().bind(stopList.getSelectionModel().selectedItemProperty().isNull());

        // 리스트 선택한게 바뀌면 할 동작 설정
        stopList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BusStopInfo>() {

            @Override
            public void changed(ObservableValue<? extends BusStopInfo> observable, BusStopInfo oldValue, BusStopInfo newValue) {
                if (newValue == null) {
                    // 뭔가의 사유로 리스트 선택이 풀리면 (ex. 검색 새로 함) 옆 이미지하고 위 이름도 지움
                    stopName.setText("버스 정류장을 선택해주세요");
                    mapImage.setImage(null);
                } else {
                    // 옆쪽에 지도와 이름을 띄움
                    stopName.setText(newValue.getName());
                    loadImage(newValue);
                }
            }
        });

        // 전체 버스정류장 목록 표시해 줌
        searchStop("");
    }

    private void searchStop(String query) {
        BusStopTask task = new BusStopTask(query);
        list = FXCollections.observableArrayList();

        task.setOnSucceeded(workerStateEvent -> {
            list.addAll(task.getValue());
        });

        stopList.setItems(list);

        Thread thread = new Thread(task, "BusStopTask-thread");
        thread.setDaemon(true);
        thread.start();
    }

    private void loadImage(BusStopInfo stopInfo) {
        String url = "https://api.vworld.kr/req/image?service=image&version=2.0&request=getmap&key=D2F1A123-6FA6-3D50-84E6-FB00971BD857&format=png&basemap=GRAPHIC&center=" + stopInfo.getMapX() + "," + stopInfo.getMapY() + "&crs=epsg:4326&zoom=16&size=500,500&marker=point%3A"+ stopInfo.getMapX() +"%20" + stopInfo.getMapY();
        ImageLoadTask task = new ImageLoadTask(url);

        task.setOnSucceeded(workerStateEvent -> {
            mapImage.setImage(task.getValue());
        });

        Thread thread = new Thread(task, "ImageLoadTask-thread");
        thread.setDaemon(true);
        thread.start();
    }


    // 아래는 버튼 Handling
    public void showAll(ActionEvent actionEvent) {
        searchStop("");
    }

    public void searchStop(ActionEvent actionEvent) {
        if (!searchField.getText().isBlank()) {
            searchStop(searchField.getText());
        }
    }

    public void openRouteOnBusStop(ActionEvent actionEvent){
        Parent root;
        try{
            BusStopInfo busStop = stopList.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/routeOnBusStop.fxml"));
            root = (Parent) loader.load();

            routeOnBusStopController controller = loader.getController();
            controller.searchBus(busStop);

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("경유 노선");
            stage.setScene(scene);

            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFoodRecom(ActionEvent actionEvent) {
        Parent root;
        try {
            BusStopInfo busStop = stopList.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/foodRecom.fxml"));
            root = (Parent) loader.load();

            FoodRecomController controller = loader.getController();
            controller.searchFood(busStop);

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("주변 음식점");
            stage.setScene(scene);

            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
