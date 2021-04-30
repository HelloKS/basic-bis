package idol.tofu.client.persistence;

public class Session {
    private String id;
    private boolean isManager;

    public Session(String id) {
        super();
        this.id = id;
        this.isManager = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
