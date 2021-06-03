package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.BusDAO;
import kumoh.basicbis.persistence.BusDTO;
import kumoh.basicbis.persistence.RouteDAO;
import kumoh.basicbis.persistence.RouteLinkDAO;

import java.util.ArrayList;

public class BusService implements BaseService {
    //TODO : 구현해나가면서 필요한 CODE 작성
    private enum Indicator {
        CODE(1),
        START_BUS_STOP_UID(2),
        END_BUS_STOP_UID(3),
        BUS_ID(2);

        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }

    private enum Code {
        UNKNOWN(0),
        BUS_LIST_BY_BUS_STOP_UID(1),
        ALL_BUS_DETAIL(2),
        BUS_LIST_BY_BUS_ID(3),
        BUS_RECOMMENDATION(4);

        private final int value;

        Code(int value) {
            this.value = value;
        }
    }

    BusDAO busDAO = new BusDAO();
    RouteDAO routeDAO = new RouteDAO();

    /*
     * reqText example
     * code 1: st_uid,st_uid
     *
     * resText example
     * code 1: rt_uid,rt_id,rt_name,rt_uid,rt_id,...
     * */
    @Override
    public String processRequest(String reqText) {
        String result = null;
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];

        switch (code) {
            case BUS_LIST_BY_BUS_STOP_UID:
                String firstBusStop = parsedText[Indicator.START_BUS_STOP_UID.value];
                String endBusStop = parsedText[Indicator.END_BUS_STOP_UID.value];
                result = busListProvider(firstBusStop, endBusStop);
                break;
            case ALL_BUS_DETAIL:
                result = allBusDetailProvider();
            case BUS_LIST_BY_BUS_ID:
                result = busListByIdProvider(parsedText[Indicator.BUS_ID.value]);
            case BUS_RECOMMENDATION:
                firstBusStop = parsedText[Indicator.START_BUS_STOP_UID.value];
                endBusStop = parsedText[Indicator.END_BUS_STOP_UID.value];
                result = busRecommendationProvider(firstBusStop, endBusStop);
            default:
                break;
        }
        return result;
    }

    private String busListProvider(String firstBusStop, String endBusStop) {
        ArrayList<BusDTO> list;
        StringBuilder stringBuilder = new StringBuilder();
        list = busDAO.getBusByBusStop(firstBusStop, endBusStop);
        int leftBusStop;
        for (BusDTO index : list) {
            leftBusStop = calculateLeftBusStop(index.getUid(),firstBusStop,endBusStop);
            if(leftBusStop > 0)
                stringBuilder.append(index.toString()).append(leftBusStop).append("\r\n");
        }
        return stringBuilder.toString();
    }

    private String allBusDetailProvider() {
        ArrayList<BusDTO> list;
        StringBuilder stringBuilder = new StringBuilder();
        list = busDAO.getAllBusDetail();
        for (BusDTO index : list) {
            stringBuilder.append(index.toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }

    private String busListByIdProvider(String requestBody) {
        ArrayList<BusDTO> list;
        StringBuilder stringBuilder = new StringBuilder();
        list = busDAO.getBusDetailById(requestBody);
        for (BusDTO index : list) {
            stringBuilder.append(index.toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }

    private String busRecommendationProvider(String firstBusStop, String endBusStop){
        ArrayList<BusDTO> list;

        int countIndex = 0, comparisonCount;
        int first,second,third;
        first = second = third = 100;
        int firstIndex,secondIndex,thirdIndex;
        firstIndex = secondIndex = thirdIndex = 0;
        StringBuilder stringBuilder = new StringBuilder();
        //두 정거장을 지나는 모든 버스정보
        list = busDAO.getBusByBusStop(firstBusStop,endBusStop);

        for(BusDTO index : list){
            comparisonCount = calculateLeftBusStop(index.getUid(), firstBusStop, endBusStop);

            if(comparisonCount < first){
                first = comparisonCount;
                firstIndex = countIndex;
            }
            else if(comparisonCount < second){
                second = comparisonCount;
                secondIndex = countIndex;
            }
            else if(comparisonCount < third){
                third = comparisonCount;
                thirdIndex = countIndex;
            }
            countIndex++;
        }

        stringBuilder.append(list.get(firstIndex).toString()).append("\r\n");
        stringBuilder.append(list.get(secondIndex).toString()).append("\r\n");
        stringBuilder.append(list.get(thirdIndex).toString()).append("\r\n");

        return stringBuilder.toString();
    }

    private int calculateLeftBusStop(String busUid, String firstBusStop, String endBusStop) {
        int firstNumber = routeDAO.getRouteNumber(busUid,firstBusStop);
        int secondNumber = routeDAO.getRouteNumber(busUid,endBusStop);
        return (secondNumber - firstNumber);
    }
}
