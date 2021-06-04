package kumoh.basicbis.persistence;


public class FoodInfo {
    private int uid;
    private String name;
    private String address;
    private String phone;
    private double mapx;
    private double mapy;

    public FoodInfo() {
    }

    public FoodInfo(int uid, String name, String address, String phone, double mapx, double mapy) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getMapx() {
        return mapx;
    }

    public void setMapx(double mapx) {
        this.mapx = mapx;
    }

    public double getMapy() {
        return mapy;
    }

    public void setMapy(double mapy) {
        this.mapy = mapy;
    }

    @Override
    public String toString() {
        return uid +
                "," + name +
                "," + address +
                "," + phone +
                "," + mapx +
                "," + mapy +
                ",";
    }
}