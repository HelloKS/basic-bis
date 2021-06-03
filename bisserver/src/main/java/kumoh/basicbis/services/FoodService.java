package kumoh.basicbis.services;

import kumoh.basicbis.persistence.BusStopDAO;
import kumoh.basicbis.persistence.BusStopDTO;
import kumoh.basicbis.persistence.FoodDAO;
import kumoh.basicbis.persistence.FoodDTO;

import java.util.ArrayList;

public class FoodService implements BaseService {
    private enum Indicator{
        CODE(1),
        BUS_STOP_UID(2);

        private final int value;
        Indicator(int value){this.value = value;}
    }
    private enum Code{
        UNKNOWN(0),
        FOOD_RECOMMENDATION(1);
        private final int value;
        Code(int value){this.value = value;}
    }
    BusStopDAO busStopDAO = new BusStopDAO();
    FoodDAO foodDAO = new FoodDAO();

    @Override
    public String processRequest(String reqText) {
        String result = null;
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];

        switch (code){
            case FOOD_RECOMMENDATION:
            result = recommendationProvider(parsedText[Indicator.BUS_STOP_UID.value]);
        }
        return result;
    }

    private String recommendationProvider(String busStopServiceUid){
        ArrayList<BusStopDTO> busStop;
        ArrayList<FoodDTO> list;
        StringBuilder stringBuilder = new StringBuilder();
        busStop = busStopDAO.getBusStopByBusStopServiceId(Integer.parseInt(busStopServiceUid));
        list = foodDAO.getFoodbyMap(busStop.get(0).getMapX(), busStop.get(0).getMapY());
        for(FoodDTO index : list){
            stringBuilder.append(index.toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
