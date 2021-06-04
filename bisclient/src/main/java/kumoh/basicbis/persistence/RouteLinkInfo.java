package kumoh.basicbis.persistence;

// RouteLink: 노선 경로
public class RouteLinkInfo {
    private int routeOrder;
    private int busStopUid;
    private String busStopName;

    public RouteLinkInfo() { }

    public RouteLinkInfo(int routeOrder, int busStopUid, String busStopName) {
        this.routeOrder = routeOrder;
        this.busStopUid = busStopUid;
        this.busStopName = busStopName;
    }

    public int getRouteOrder() {
        return routeOrder;
    }

    public void setRouteOrder(int routeOrder) {
        this.routeOrder = routeOrder;
    }

    public int getBusStopUid() {
        return busStopUid;
    }

    public void setBusStopUid(int busStopUid) {
        this.busStopUid = busStopUid;
    }

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    @Override
    public String toString() {
        return routeOrder + "," +
                busStopUid + "," +
                busStopName;
    }
}
