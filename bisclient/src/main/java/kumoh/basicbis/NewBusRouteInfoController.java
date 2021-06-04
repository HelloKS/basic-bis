package kumoh.basicbis;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.util.BusStopTask;
import kumoh.basicbis.util.RouteTask;

import java.net.URL;
import java.util.ResourceBundle;

public class NewBusRouteInfoController implements Initializable {

    @FXML ListView<RouteInfo> routeList;
    @FXML TextField searchField;
    @FXML Button showTimetable;
    @FXML Button showRunningBus;
    @FXML TableView<BusStopInfo> routeLinkList;
    @FXML Label routeName;

    private ObservableList<RouteInfo> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 리스트뷰 표시 Cell factory
        routeList.setCellFactory(busStopInfoListView -> new RouteInfoListViewCell());

        // 리스트 클릭 안되면 버튼 비활성화하게끔 지정
        showTimetable.disableProperty().bind(routeList.getSelectionModel().selectedItemProperty().isNull());
        showRunningBus.disableProperty().bind(routeList.getSelectionModel().selectedItemProperty().isNull());

        // 리스트 선택한게 바뀌면 할 동작 설정
        routeList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RouteInfo>() {

            @Override
            public void changed(ObservableValue<? extends RouteInfo> observable, RouteInfo oldValue, RouteInfo newValue) {
                if (newValue == null) {
                    // 뭔가의 사유로 리스트 선택이 풀리면 (ex. 검색 새로 함) 옆에 다 지움
                    routeName.setText("운행 노선을 선택해주세요");
                } else {
                    // 옆쪽에 경유정류장과 이름을 띄움
                    routeName.setText(newValue.getName());
                }
            }
        });

        // 전체 버스정류장 목록 표시해 줌
        searchRoute("");
    }

    private void searchRoute(String query) {
        RouteTask task = new RouteTask(query);
        list = FXCollections.observableArrayList();

        task.setOnSucceeded(workerStateEvent -> {
            list.addAll(task.getValue());
        });

        routeList.setItems(list);

        Thread thread = new Thread(task, "RouteTask-thread");
        thread.setDaemon(true);
        thread.start();
    }


    // 아래는 버튼 Handling
    public void showAll(ActionEvent actionEvent) {
        searchRoute("");
    }

    public void searchStop(ActionEvent actionEvent) {
        if (!searchField.getText().isBlank()) {
            searchRoute(searchField.getText());
        }
    }
}
