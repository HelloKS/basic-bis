package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.BusStopDAO;
import kumoh.basicbis.persistence.BusStopDTO;

import java.util.ArrayList;

public class BusStopService implements BaseService {
    public enum Indicator {
        CODE(1),
        BUS_STOP_NAME(2);
        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }

    public enum Code {
        UNKNOWN(0),
        BUS_STOP_BY_BUS_NAME(1);

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
}
