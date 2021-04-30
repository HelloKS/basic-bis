package idol.tofu.server.persistence;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;


public class BillingDTO {
    private int uid;
    private int amount; //결제 총액
    private int bank; //숫자별 은행 이름 매핑
    private String accountNumber; //계좌 번호
    private int billingStatus; //정수별 결제 상태 매핑 0:입금대기중 1:결제완료 2:환불완료
    private Timestamp billingDate; //결제일시

    public BillingDTO() {}
    public BillingDTO(int uid, int amount, int bank, String accountNumber, int billingStatus, Timestamp billingDate) {
        this.uid = uid;
        this.amount = amount;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.billingStatus = billingStatus;
        this.billingDate = billingDate;
    }

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount; }

    public int getBank() { return bank; }

    public void setBank(int bank) { this.bank = bank; }

    public String getAccountNumber() { return accountNumber; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public int getBillingStatus() { return billingStatus; }

    public void setBillingStatus(int billingStatus) { this.billingStatus = billingStatus; }

    public Timestamp getBillingDate() { return billingDate; }

    public void setBillingDate(Timestamp billingDate) { this.billingDate = billingDate; }

    @Override
    public String toString() {
        return "" +uid + ',' +
                amount + ',' +
                bank + ',' +
                accountNumber + ',' +
                billingStatus + ',' +
                billingDate;
    }
}
