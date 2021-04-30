package idol.tofu.client.persistence;

/*
Server: TheaterDTO.java 기반
 */

public class TheaterInfo {
    private int uid;
    private int bank; // 영화관 수익 계좌 은행
    private String name;
    private String address; // 영화관 주소
    private String phoneNumber; // 영화관 전화번호
    private String accountNumber; // 수익 계좌 번호

    public TheaterInfo() {}

    public TheaterInfo(int uid, String name, String address, String phoneNumber) {
        this.uid = uid;
        this.bank = bank;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getBank() { return bank; }

    public void setBank(int bank) { this.bank = bank; }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "Theater{" +
                "uid=" + uid + '\'' +
                "name=" + name + '\'' +
                "address=" + address + '\'' +
                "phoneNumber=" + phoneNumber + '\'' +
                '}';
    }
}

