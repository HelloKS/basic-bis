package kumoh.basicbis.persistence;

import java.io.Serializable;

public class RouteDTO implements Serializable {
    private String busId;
    private String busStopId;
    private int routeNumber;

    public RouteDTO() {}

    public RouteDTO(String busId, String busStopId, int routeNumber) {
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
