package kumoh.basicbis.services;

import kumoh.basicbis.persistence.BusStopDAO;
import kumoh.basicbis.persistence.BusStopDTO;

import java.util.ArrayList;

public class BusStopService implements BaseService {
    private enum Indicator {
        CODE(1),
        BUS_STOP_NAME(2),
        BUS_STOP_UID(2);
        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }

    private enum Code {
        UNKNOWN(0),
        BUS_STOP_BY_BUS_NAME(1),
        BUS_STOP_BY_UID(2);

        private final int value;

        Code(int value) {
            this.value = value;
        }
    }

    BusStopDAO busStopDAO = new BusStopDAO();

    /*
     * reqText example
     * code 1: st_name
     *
     * resText example
     * code 1: st_uid,st_svcid,st_name,st_mapx,st_mapy,st_uid,...
     * */
    @Override
    public String processRequest(String reqText) {
        String result = null;
        String[] parsedText = reqText.split(",");

        int codeIndex = Integer.parseInt(parsedText[Indicator.CODE.value]);
        BusStopService.Code code = Code.values()[codeIndex];

        switch (code) {
            case BUS_STOP_BY_BUS_NAME:
                result = busStopListProvider(parsedText[Indicator.BUS_STOP_NAME.value]);
                break;
            case BUS_STOP_BY_UID:
                result = busStopProvider(parsedText[Indicator.BUS_STOP_UID.value]);
            default:
                break;
        }
        return result;
    }

    private String busStopListProvider(String requestBody) {
        ArrayList<BusStopDTO> list;
        StringBuilder stringBuilder = new StringBuilder();
        list = busStopDAO.getBusStopByBusName(requestBody);
        for (BusStopDTO index : list) {
            stringBuilder.append(index.toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }

    private String busStopProvider(String requestBody){
        ArrayList<BusStopDTO> list;
        StringBuilder stringBuilder = new StringBuilder();
        list = busStopDAO.getBusStopByBusStopServiceId(Integer.parseInt(requestBody));
        for (BusStopDTO index : list) {
            stringBuilder.append(index.toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
