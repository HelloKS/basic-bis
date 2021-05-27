package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.BusStopDAO;
import kumoh.basicbis.persistence.BusStopDTO;

import java.util.ArrayList;

public class BusStopService implements BaseService {
    public enum Indicator {
        CODE(1),
        BUS_STOP_NAME(3);
        private final int value;
        Indicator(int value){this.value = value;}
    }

    public enum Code {
        BUS_STOP_BY_BUS_NAME(1);

        private final int value;

        Code(int value) {
            this.value = value;
        }
    }

    /*
    * reqText example
    * code 1: ProtocolType,Code,BusStopName
    *
    * resText example
    * code 1: ProtocolType,Code,st_uid,st_svcid,st_name,st_mapx,st_mapy,st_uid,...
    * */
    @Override
    public String processRequest(String reqText) {
        String result = null;
        StringBuilder funcResult = new StringBuilder();
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];
        final int TYPE = ProtocolType.BUS_STOP_REQ.getType();

        switch (code)
        {
            case BUS_STOP_BY_BUS_NAME:
            {
                BusStopDAO busStopDAO = new BusStopDAO();
                ArrayList<BusStopDTO> list = busStopDAO.getBusStopByBusName(parsedText[Indicator.BUS_STOP_NAME.value]);
                for(BusStopDTO index : list)
                {
                    funcResult.append(index.toString()).append(",");
                }
                break;
            }
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = TYPE + "," + code.value + "," + funcResult.toString();
        return result;
    }
}
