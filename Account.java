package teamProject;

public class Account {
    private int id;
    private String member_id;
    private int account_type;
    private String account_number;
    private double balance;
    private double interest_rate;
    private double fee_rate;

    public Account() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getAccount_type() {
        return account_type;
    }

    public void setAccount_type(int account_type) {
        this.account_type = account_type;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getInterest_rate() {
        return interest_rate;
    }

    public void setInterest_rate(double interest_rate) {
        this.interest_rate = interest_rate;
    }

    public double getFee_rate() {
        return fee_rate;
    }

    public void setFee_rate(double fee_rate) {
        this.fee_rate = fee_rate;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", member_id='" + member_id + '\'' +
                ", account_type='" + account_type + '\'' +
                ", account_number='" + account_number + '\'' +
                ", balance=" + balance +
                ", interest_rate=" + interest_rate +
                ", fee_rate=" + fee_rate +
                '}';
    }

    public Account(int id, String member_id, int account_type, String account_number, double balance, double interest_rate, double fee_rate) {
        this.id = id;
        this.member_id = member_id;
        this.account_type = account_type;
        this.account_number = account_number;
        this.balance = balance;
        this.interest_rate = interest_rate;
        this.fee_rate = fee_rate;
    }
}