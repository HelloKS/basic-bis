package idol.tofu.server;

public enum ProtocolType {
    UNKNOWN(0),  // =0 X ="0" "TOSS"
    REG_REQ(1), REG_RES(2),       //회원가입  ("0x01" , "0x02")
    LOG_REQ(3), LOG_RES(4),   //로그인  ("0x03" , "0x04")
    THTR_REQ(5), THTR_RES(6),     //영화관 정보  ("0x05" , "0x06")
    RVW_REQ(7), RVW_RES(8),       //리뷰  ("0x07" , "0x08")
    MOV_REQ(9), MOV_RES(10),       //영화  ("0x09" , "0x10")
    SCR_REQ(11), SCR_RES(12),       //상영관  ("0x11" , "0x12")
    SCH_REQ(13), SCH_RES(14),       //스케줄  ("0x13" , "0x14")
    TICKET_REQ(15), TICKET_RES(16), //예매  ("0x15" , "0x16")
    BILL_REQ(17), BILL_RES(18),     //결제  ("0x17" , "0x18")
    REFUND_REQ(19), REFUND_RES(20), //환불  ("0x19" , "0x20")
    PAYOUT_REQ(21), PAYOUT_RES(22), // 수익정산  ("0x21" , "0x22")
    MANAGE_REQ(23), MANAGE_RES(24), //관리  ("0x23" , "0x24")
    SEAT_STATUS_REQ(25), SEAT_STATUS_RES(26), //좌석 상태  ("0x25" , "0x26")
    RESERVE_REQ(27), RESERVE_RES(28); //예매  ("0x27" , "0x28")

    private int value;

    ProtocolType(int value) { this.value = value; }

    public int getType() { return value;}

}