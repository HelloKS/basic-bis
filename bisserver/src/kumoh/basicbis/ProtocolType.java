package kumoh.basicbis;

public enum ProtocolType {
    UNKNOWN(0),  // =0 X ="0" "TOSS"
    //TODO: 여기에 프로토콜 입력, 더 필요할시 문서에 적어주세용~
    BUS_STOP_REQ(1), BUS_STOP_RES(2),
    ROUTE_REQ(3), ROUTE_RES(4),
    FOOD_REQ(5), FOOD_RES(6);

    private int value;

    ProtocolType(int value) { this.value = value; }

    public int getType() { return value;}

}