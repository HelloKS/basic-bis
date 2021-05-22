package kumoh.basicbis.persistence;

import java.io.Serializable;


// RouteLink: 노선 경로
public class RouteLinkDTO implements Serializable {
    private String busId;
    private String busStopId;
    private int routeNumber;

    public RouteLinkDTO() {}

    public RouteLinkDTO(String busId, String busStopId, int routeNumber) {
        this.busId = busId;
        this.busStopId = busStopId;
        this.routeNumber = routeNumber;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusStopId() {
        return busStopId;
    }

    public void setBusStopId(String busStopId) {
        this.busStopId = busStopId;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    @Override
    public String toString() {
        return busId +"," +
                busStopId + "," +
                routeNumber + ",";
    }
}
