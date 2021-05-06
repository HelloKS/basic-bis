package kumoh.basicbis.persistence;

import java.io.Serializable;

public class BusStopDTO implements Serializable {
    private int uid;
    private int serviceId;
    private String name;
    private double mapX;
    private double mapY;

    public BusStopDTO() { }

    public BusStopDTO(int uid, int serviceId, String name, double mapX, double mapY) {
        this.uid = uid;
        this.serviceId = serviceId;
        this.name = name;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMapX() {
        return mapX;
    }

    public void setMapX(double mapX) {
        this.mapX = mapX;
    }

    public double getMapY() {
        return mapY;
    }

    public void setMapY(double mapY) {
        this.mapY = mapY;
    }
}
