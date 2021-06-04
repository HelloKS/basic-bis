package kumoh.basicbis;

import javafx.beans.property.ReadOnlyStringWrapper;
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
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.persistence.RouteInfo;
import kumoh.basicbis.util.BusStopTask;
import kumoh.basicbis.util.RouteTask;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewBusRouteInfoController implements Initializable {

    @FXML ListView<RouteInfo> routeList;
    @FXML TextField searchField;
    @FXML Button showTimetable;
    @FXML Button showRunningBus;
    @FXML TableView<BusStopInfo> routeLinkTable;
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
                    routeName.setText(newValue.getId() + " (" +newValue.getName() + ")");
                }
            }
        });

        // 옆쪽 경유하는 버스 정류장 테이블 행 만들어 줌
        routeLinkTable.getColumns().setAll(getColumns());


        // 전체 버스정류장 목록 표시해 줌
        searchRoute("");
    }

    public TableColumn[] getColumns() {
        final TableColumn<Void, String> indexColumn = new TableColumn<>("운행 순번");
        indexColumn.setCellFactory(item -> new TableCell<>() {
            @Override
            public void updateIndex(int index) {
                super.updateIndex(index);
                if (isEmpty() || index < 0) {
                    setText(null);
                } else {
                    setText(Integer.toString(index));
                }
            }
        });

        final TableColumn<BusStopInfo, String> busStopColumn = new TableColumn<>("정차 정류장");
        busStopColumn.setCellValueFactory(item -> new ReadOnlyStringWrapper(item.getValue().getName()));
        busStopColumn.setPrefWidth(400);

        return new TableColumn[]{indexColumn, busStopColumn};
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

    public void openTimetable(ActionEvent actionEvent) {
        Parent root;
        try {
            RouteInfo route = routeList.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/busTimeTable.fxml"));
            root = (Parent) loader.load();

            TimetableController controller = loader.getController();
            controller.initData(route);

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("기점 출발 시간표");
            stage.setScene(scene);

            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}