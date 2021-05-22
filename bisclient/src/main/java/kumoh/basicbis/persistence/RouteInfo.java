package kumoh.basicbis.persistence;

// Route: 노선
public class RouteInfo {
    private String uid;
    private String id;
    private String name;

    public RouteInfo() {
    }

    public RouteInfo(String uid, String id, String name) {
        this.uid = uid;
        this.id = id;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return uid + ',' +
                id + ',' +
                name + '\n';
    }
}
