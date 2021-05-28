package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.BusDAO;
import kumoh.basicbis.persistence.BusDTO;
import kumoh.basicbis.persistence.RouteLinkDAO;

import java.util.ArrayList;

public class BusService implements BaseService{
    //TODO : 구현해나가면서 필요한 CODE 작성
    public enum Indicator{
        CODE(1),
        START_BUS_STOP_UID(2),
        END_BUS_STOP_UID(3);

        private final int value;
        Indicator(int value){
            this.value = value;
        }
    }
    public enum Code{
        UNKNOWN(0),
        BUS_LIST_BY_BUS_STOP_UID(1);

        private final int value;
        Code(int value){
            this.value = value;
        }
    }

    /*
    * reqText example
    * code 1: ProtocolType,Code,st_uid,st_uid
    *
    * resText example
    * code 1: ProtocolType,code,rt_uid,rt_id,rt_name,rt_uid,rt_id,...
    * */
    @Override
    public String processRequest(String reqText) {
        String result = null;
        String sqlResult = null;
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];
        final int TYPE = ProtocolType.ROUTE_REQ.getType();

        switch (code){
            case BUS_LIST_BY_BUS_STOP_UID: {
                String firstBusStop = parsedText[Indicator.START_BUS_STOP_UID.value];
                String endBusStop = parsedText[Indicator.END_BUS_STOP_UID.value];
                sqlResult = busListProvider(firstBusStop, endBusStop);
                break;
            }
            default:
                break;
        }
        result = TYPE + "," + code.value + "," + sqlResult.toString();
        return result;
    }

    private String busListProvider(String firstBusStop, String endBusStop)
    {
        ArrayList<BusDTO> list;
        BusDAO busDAO = new BusDAO();
        StringBuilder stringBuilder = new StringBuilder();
        list = busDAO.getBusByBusStop(firstBusStop, endBusStop);
        for(BusDTO index : list)
        {
            stringBuilder.append(index.toString()).append(",");
        }
        return stringBuilder.toString();
    }
}
