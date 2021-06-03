package kumoh.basicbis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import kumoh.basicbis.persistence.BusInfo;
import kumoh.basicbis.persistence.BusStopInfo;
import kumoh.basicbis.util.BusInfoTask;
import kumoh.basicbis.util.BusStopTask;
import kumoh.basicbis.util.BusStopTask2;

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

    //textField확인용
    int check = 0;
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
                rtTextField1.setText(name.getName());
            }
            else if(check == 2){
                rtTextField2.setText(name.getName());
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

    //해당 정류장2개를가는 버스검색
    public void rtSearchBus(ActionEvent actionEvent) {
        System.out.print("ㅎㅇㅎㅇ");
        String query = rtTextField1.getText() + ',' + rtTextField2.getText();
        searchRtBus(query);
    }

    //정류장 이름 선택
    public void selectName(ActionEvent actionEvent) {
        if(stopName.isSelected()) {check = 1;}
        else check = 0;

    }
    //정류장 번호 선택
    public void selectNumber(ActionEvent actionEvent) {
        if(stopNumber.isSelected()) {check = 2;}
        else check = 0;
    }
    //정류장 검색 마우스 클릭 이벤트 -> 해당 정류장을 지나치는 버스들 목록 나타내기
    public void bsSelected(MouseEvent mouseEvent) {
    }
    //버튼검색
    public void bsBt(ActionEvent actionEvent) {
        if(check == 1) {
            searchRt(bsTextField.getText(),2);
        } else if(check == 2) {
            searchRtId(bsTextField.getText());
        }
    }

    //
}
