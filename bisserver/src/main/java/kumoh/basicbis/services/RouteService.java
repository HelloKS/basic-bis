package kumoh.basicbis.services;

import kumoh.basicbis.ProtocolType;
import kumoh.basicbis.persistence.*;

import java.util.ArrayList;

public class RouteService implements BaseService{

    private enum Indicator {
        CODE(1),
        BUS_NAME(2),
        BUS_UID(2),
        BUS_STOP_SERVICE_ID(2);
        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }
    private enum ServiceType{
        UNKNOWN(0),
        ROUTE_LIST(1),
        TIMETABLE(2),
        BUS_ALL_DETAIL(3),
        ROUTE_LINK(4),
        BUS_ON_BUS_STOP(5);

        private final int value;
        ServiceType(int value){
            this.value = value;
        }
    }
    BusDAO busDAO = new BusDAO();
    TimeTableDAO timeTableDAO = new TimeTableDAO();
    RouteDAO routeDAO = new RouteDAO();
    RouteLinkDAO linkDAO = new RouteLinkDAO();

    /*
    * result example
    * code1 : rt_uid,rt_id,rt_name,\r\nrt_uid,rt_id,....
    * code2 : rt_uid,rt_id,rt_name,\r\nfirstTime,endTime,\r\nst_name,st_name,st_name,...
    * */
    @Override
    public String processRequest(String reqText) {
        String result = "";
        ServiceType svcType = ServiceType.UNKNOWN;

        String[] reqText_split = reqText.split(",");
        try {
            int svc = Integer.parseInt(reqText_split[Indicator.CODE.value]);
            svcType = ServiceType.values()[svc];
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (svcType) {
            case ROUTE_LIST:
                if (reqText_split.length == 2) {
                    result = routeListProvider("");
                } else {
                    result = routeListProvider(reqText_split[Indicator.BUS_NAME.value]);
                }
                break;
            case BUS_ALL_DETAIL:
                result = busAllDetailProvider(reqText_split[Indicator.BUS_UID.value]);
                break;
            case ROUTE_LINK:
                result = routeLinkListProvider(reqText_split[2]);
                break;
            case BUS_ON_BUS_STOP:
                result = busOnBusStopProvider(reqText_split[Indicator.BUS_STOP_SERVICE_ID.value]);
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

    private String routeLinkListProvider(String query) {
        ArrayList<RouteLinkDTO> routeLinks;
        StringBuilder sbuilder = new StringBuilder();

        routeLinks = linkDAO.getRouteLink(query);

        for (RouteLinkDTO route : routeLinks) {
            sbuilder.append(route.toString()).append("\r\n");
        }

        return sbuilder.toString();
    }

    private String busAllDetailProvider(String requestBody){
        ArrayList<BusDTO> busDTOArrayList;
        ArrayList<TimeTableDTO> timeTableDTOList;
        StringBuilder stringBuilder = new StringBuilder();

        busDTOArrayList = busDAO.getBusDetailById(requestBody);
        timeTableDTOList = timeTableDAO.getFirstLastTimetable(requestBody);

        for(BusDTO index : busDTOArrayList){
            stringBuilder.append(index.toString()).append(",");
        }
        for(TimeTableDTO index : timeTableDTOList){
            stringBuilder.append(index.toString()).append("\r\n");
        }
        stringBuilder.append(routeDAO.getRouteByBusId(requestBody)).append("\r\n");

        return stringBuilder.toString();
    }

    private String busOnBusStopProvider(String requestBody){
        ArrayList<BusDTO> busDTOArrayList;
        StringBuilder stringBuilder = new StringBuilder();

        busDTOArrayList = routeDAO.getBusByBusStop(requestBody);
        for(BusDTO index : busDTOArrayList){
            stringBuilder.append(index.toString()).append("\r\n");
        }
        return stringBuilder.toString();
    }
}
