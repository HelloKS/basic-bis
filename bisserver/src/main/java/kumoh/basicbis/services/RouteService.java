package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.RouteDAO;
import kumoh.basicbis.persistence.RouteDTO;

import java.util.ArrayList;

public class RouteService implements BaseService{

    //TODO : 구현해나가면서 필요한 CODE 작성

    RouteDAO routeDAO = new RouteDAO();

    //서비스 코드
    private enum ServiceType{
        UNKNOWN(0),
        ROUTE_LIST(1),
        TIMETABLE(2);

        private final int value;
        ServiceType(int value){
            this.value = value;
        }
    }

    @Override
    public String processRequest(String reqText) {
        String result = "";
        ServiceType svcType = ServiceType.UNKNOWN;

        String[] reqText_split = reqText.split(",");
        //String[] serviceHeader = reqText_split[0].split(",");

        try {
            int svc = Integer.parseInt(reqText_split[1]);
            svcType = ServiceType.values()[svc];
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (svcType) {
            case ROUTE_LIST:
                if (reqText_split.length == 2) {
                    result = routeListProvider("");
                } else {
                    result = routeListProvider(reqText_split[2]);
                }
                break;
            case TIMETABLE:
                break;
            default:
                break;
        }

        return result;
    }

    private String routeListProvider(String query) {
        ArrayList<RouteDTO> routes;
        StringBuilder sbuilder = new StringBuilder();

        routes = routeDAO.getRouteByName(query);

        for (RouteDTO route : routes) {
            sbuilder.append(route.toString()).append("\r\n");
        }

        return sbuilder.toString();
    }
}
