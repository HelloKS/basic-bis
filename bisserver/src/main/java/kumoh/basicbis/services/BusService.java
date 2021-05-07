package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.BusDAO;
import kumoh.basicbis.persistence.BusDTO;

import java.util.ArrayList;

public class BusService implements BaseService{
    //TODO : 구현해나가면서 필요한 CODE 작성
    public enum Indicator{
        CODE(1);
        private final int value;
        Indicator(int value){
            this.value = value;
        }
    }
    public enum Code{
        BUS_LIST(1),
        BUS_LIST_RES(1);

        private final int value;
        Code(int value){
            this.value = value;
        }
    }

    /*
    * reqText example
    * code 1: ProtocolType, code
    *
    * resText example
    * code 1: ProtocolType, code, rt_uid, rt_id, rt_name, rt_uid, rt_id, ...
    * */
    @Override
    public String processRequest(String reqText) {
        String result = null;
        StringBuilder funcResult = new StringBuilder();
        BusDAO busDAO = new BusDAO();
        ArrayList<BusDTO> list = null;

        int type = ProtocolType.ROUTE_RES.getType();
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];

        switch (code){
            case BUS_LIST: { //버스시간표 안내 화면 - 노선정보 요청
                // parsedText structure : ProtocolType,code,rt_uid,rt_id,rt_name,...
                list = busDAO.getAllBusDetail();
                for(BusDTO index : list)
                {
                    funcResult.append(index.toString()).append(",");
                }
                break;
            }
            default:
                break;
        }
        code = Code.values()[Code.BUS_LIST_RES.value];
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = type + "," + code.value + "," + funcResult.toString();

        return result;
    }
}
