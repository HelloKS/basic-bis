package kumoh.basicbis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import kumoh.basicbis.persistence.BusInfo;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.util.BusInfoTask;
import kumoh.basicbis.util.BusStopTask;
import kumoh.basicbis.util.BusStopTask2;

import java.io.IOException;
import java.util.List;

public class SearchController {
    /* 일단 경로 검색 */
    @FXML private Button rtBt1;
    @FXML private Button rtBt2;
    @FXML private Button rtBt3;
    @FXML private TextField rtTextField1;
    @FXML private TextField rtTextField2;
    @FXML private ListView<BusStopInfo> rtListView;
    @FXML private ListView<BusInfo> resultView;

    /* 정류장 검색 */
    @FXML private Button bsBt;
    @FXML private TextField bsTextField;
    @FXML private CheckBox stopName;
    @FXML private CheckBox stopNumber;
    @FXML private ListView<BusStopInfo> bsListView1;
    @FXML private ListView<BusInfo> bsListView2;

    /* 경로 추천 검색 */
    @FXML private Button rcBt1;
    @FXML private Button rcBt2;
    @FXML private Button rcBt3;
    @FXML private TextField rcTextField1;
    @FXML private TextField rcTextField2;
    @FXML private ListView<BusStopInfo> rcListView;

    //textField확인용
    int check = 0;
    BusStopInfo start, end;
    private ObservableList<BusStopInfo> list;
    private ObservableList<BusInfo> busList;

    //버스 정류장 정보 가져오기 (이름 검색)
    public void searchRt(String query, int type) {
        BusStopTask task = new BusStopTask(query);
        list = FXCollections.observableArrayList();

        task.setOnSucceeded(workerStateEvent -> {
            list.addAll(task.getValue()); }
        );

        if(type == 1) {
            rtListView.setItems(list);
            rtListView.setCellFactory(new Callback<ListView<BusStopInfo>, ListCell<BusStopInfo>>() {
                @Override
                public ListCell<BusStopInfo> call(ListView<BusStopInfo> busStopInfoListView) {
                    return new BusStopInfoListViewCell();
                }
            });
        } else if(type == 2) {
            bsListView1.setItems(list);
            bsListView1.setCellFactory(new Callback<ListView<BusStopInfo>, ListCell<BusStopInfo>>() {
                @Override
                public ListCell<BusStopInfo> call(ListView<BusStopInfo> busStopInfoListView) {
                    return new BusStopInfoListViewCell();
                }
            });
        } else if(type == 3){
            rcListView.setItems(list);
            rcListView.setCellFactory(new Callback<ListView<BusStopInfo>, ListCell<BusStopInfo>>() {
                @Override
                public ListCell<BusStopInfo> call(ListView<BusStopInfo> busStopInfoListView) {
                    return new BusStopInfoListViewCell();
                }
            });
        }

        Thread thread = new Thread(task, "BusStopTask-thread");
        thread.setDaemon(true);
        thread.start();
    }

    //버스 정류장 정보 가져오기 (serviceid 검색)
    public void searchRtId(String query) {
        BusStopTask2 task = new BusStopTask2(query);
        list = FXCollections.observableArrayList();

        task.setOnSucceeded(workerStateEvent -> {
            list.addAll(task.getValue()); }
        );

        bsListView1.setItems(list);
        bsListView1.setCellFactory(new Callback<ListView<BusStopInfo>, ListCell<BusStopInfo>>() {
            @Override
            public ListCell<BusStopInfo> call(ListView<BusStopInfo> busStopInfoListView) {
                return new BusStopInfoListViewCell();
            }
        });
        Thread thread = new Thread(task, "BusStopTask-thread");
        thread.setDaemon(true);
        thread.start();
    }

    //버스 검색
    public void searchRtBus(String query) {
        BusInfoTask task = new BusInfoTask(query);
        busList = FXCollections.observableArrayList();

        task.setOnSucceeded(workerStateEvent -> {
            busList.addAll(task.getValue()); });

        resultView.setItems(busList);
        resultView.setCellFactory(new Callback<ListView<BusInfo>, ListCell<BusInfo>>() {
            @Override
            public ListCell<BusInfo> call(ListView<BusInfo> busInfoListView) {
                return new busInfoListViewCell();
            }
        });
    }

    //리스트 마우스 선택 이벤트
    public void rtSelected(MouseEvent mouseEvent) {
        BusStopInfo name = rtListView.getSelectionModel().getSelectedItem();
        if(name != null) {
            if(check == 1) {
                start = name;
                rtTextField1.setText(start.getName());
            }
            else if(check == 2){
                end = name;
                rtTextField2.setText(end.getName());
            }
        }
    }
    public void rcSelected(MouseEvent mouseEvent){
        BusStopInfo name = rcListView.getSelectionModel().getSelectedItem();
        if(name != null){
            if(check == 3){
                start = name;
                rcTextField1.setText(start.getName());
            }
            else if(check ==4){
                end = name;
                rcTextField2.setText(end.getName());
            }
        }
    }

    //출발정류장 검색
    public void rtStartBt(ActionEvent actionEvent) {
        searchRt(rtTextField1.getText(),1);
        check = 1;
    }

    //도착정류장 검색
    public void rtEndBt(ActionEvent actionEvent) {
        searchRt(rtTextField2.getText(),1);
        check = 2;
    }
    //출발정류장 검색
    public void rcStartBt(ActionEvent actionEvent) {
        searchRt(rcTextField1.getText(),3);
        check = 3;
    }

    //도착정류장 검색
    public void rcEndBt(ActionEvent actionEvent) {
        searchRt(rcTextField2.getText(),3);
        check = 4;
    }

    //버튼검색
    public void bsBt(ActionEvent actionEvent) {
        if(check == 1) {
            searchRt(bsTextField.getText(),2);
        } else if(check == 2) {
            searchRtId(bsTextField.getText());
        }
    }
    public void openSearchResult(ActionEvent actionEvent){
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/searchResult.fxml"));
            root = (Parent)loader.load();

            searchResultController controller = loader.getController();
            controller.searchBus(start, end);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("길찾기 검색 결과");
            stage.setScene(scene);

            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void openRecommendationResult(ActionEvent actionEvent){
        Parent root;
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/searchResult.fxml"));
            root = (Parent)loader.load();

            searchResultController controller = loader.getController();
            controller.recommendation(start, end);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("노선 추천 결과");
            stage.setScene(scene);

            stage.resizableProperty().setValue(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
