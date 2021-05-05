package kumoh.basicbis;

public enum ProtocolType {
    UNKNOWN(0);  // =0 X ="0" "TOSS"
    //TODO: 여기에 프로토콜 입력

    private int value;

    ProtocolType(int value) { this.value = value; }

    public int getType() { return value;}

}