package teamProject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountManager implements IAccountManager {
    private final Scanner stdIn;
    private final AccountDao accountDao;
    private Connection connection = null;

    public AccountManager() {
        stdIn = new Scanner(System.in);
        accountDao = new AccountDao();
    }

    public void addMember(){
        Member member = new Member();
        System.out.print("아이디 : ");
        member.setMember_id(stdIn.next());

        System.out.print("이름 : ");
        member.setName(stdIn.next());

        System.out.print("나이 : ");
        member.setAge(stdIn.nextInt());

        System.out.print("거주지 : ");
        member.setAddress(stdIn.next());

        if (this.plusMember(member)) {
            System.out.println("아이디가 생성되었습니다.");
        } else {
            System.out.println("아이디 생성에 실패했습니다.");
        }
    } // 회원 등록
    private boolean plusMember(Member member) {
        if (this.isMember(member.getMember_id())) {
            System.out.println(member.getMember_id() + "는 사용중인 아이디입니다.");
            return false;
        }
        return accountDao.insertMember(member);
    }
    public void addAccount(){
        Account account = new Account();
        String account_type;

        System.out.print("아이디 : ");
        account.setMember_id(stdIn.next());

        System.out.print("계좌종류 (1: 예금계좌, 2: 대출계좌) : ");
        account.setAccount_type(stdIn.next());
        account_type = account.getAccount_type();

        System.out.print("계좌번호 : ");
        account.setAccount_number(stdIn.next());

        System.out.print("잔액 : ");
        account.setBalance(stdIn.nextDouble());

        if (account.getAccount_type().equals("1")) {
            System.out.print("이자율 : ");
            account.setInterest_rate(stdIn.nextDouble());
        } else {
            System.out.print("수수료율 : ");
            account.setFee_rate(stdIn.nextDouble());
        }

        if (this.plusAccount(account)) {
            System.out.println("계좌가 생성되었습니다.");
        } else {
            System.out.println("계좌 생성에 실패했습니다.");
        }
    }// 계좌 개설
    private boolean plusAccount(Account account) {
        if (this.isAccount(account.getAccount_number())) {
            System.out.println(account.getAccount_number() + "는 등록된 계좌번호입니다.");
            return false;
        }
        return accountDao.insertAccount(account);
    }
    public void deposit() {
        AccountHistory accountHistory = new AccountHistory();

        String transaction_type = "입금";
        accountHistory.setTransaction_type("입금");

        System.out.print("계좌번호: ");
        String account_number = stdIn.next();
        accountHistory.setAccount_number(account_number);

        System.out.print("입금액: ");
        Double transaction_amount = stdIn.nextDouble();
        accountHistory.setTransaction_amount(transaction_amount);

        // 해당 계좌 찾기
        if (this.isAccount((account_number))) {
            accountDao.insertAccountHistory(accountHistory); // 먼저 읽어야 됌
            accountDao.updateBalance(account_number, transaction_amount, true); // 그리구 계산
        } else {
            System.out.println("해당 계좌번호가 존재하지 않습니다.");
        }
        // 예금계좌
        if() {  // 이조건은 예금계좌가 맞다면 => account 계좌번호의 type값
                // 첫번째 기록 amount+getbalance

                // 첫번째 account update -> amount+getbalance

            if() {  // 첫번쨰 기록이 정상적으로 저장이된다면 정수이면 ? > 0, boolean이면 true,false
                // 두번쨰 기록 getbalance+(amount X 이자율)

                // 두번째 update -> getbalance+(amount X 이자율)

            }
        }else {

        }
        Double balance = accountDao.selectBalance(account_number) + transaction_amount * accountDao.selectAccount(account_number).getInterest_rate();
        accountHistory.setBalance(balance);


    } // 입금 처리
    public void withdraw() {
        AccountHistory accountHistory = new AccountHistory();

        String transaction_type = "출금";
        accountHistory.setTransaction_type("출금");

        System.out.print("계좌번호: ");
        String account_number = stdIn.next();
        accountHistory.setAccount_number(account_number);

        System.out.print("출금액: ");
        Double transaction_amount = stdIn.nextDouble();
        accountHistory.setTransaction_amount(transaction_amount);

        // 해당 계좌 찾기
        if (this.isAccount((account_number))) {
            accountDao.insertAccountHistory(accountHistory);
            accountDao.updateBalance(account_number, transaction_amount, false);
        } else {
            System.out.println("해당 계좌번호가 존재하지 않습니다.");
        }

        Double balance = accountDao.selectBalance(account_number) - transaction_amount * accountDao.selectAccount(account_number).getFee_rate();
        accountHistory.setBalance(balance);
    } // 출금 처리
    public void viewHistory() {
        System.out.print("계좌번호 : ");
        String account_number = stdIn.next();
        ArrayList<AccountHistory> list = accountDao.selectAccountHistories(account_number);
        for (AccountHistory accountHistory : list) {
            System.out.printf(accountHistory.getTransaction_type() + "\t" + accountHistory.getTransaction_amount() + "\t" + accountHistory.getBalance());
        }
        System.out.printf("잔액: " + accountDao.selectAccount(account_number).getBalance());

    } // 조회 처리
    public void disConnect() {
        accountDao.disConnect();
        stdIn.close();
    } // 종료 처리
    public boolean isMember(String member_id) {
        return accountDao.selectMemberIdCnt(member_id) == 1;
    };
    public boolean isAccount(String account_number) {
        return accountDao.selectAccountIdCnt(account_number) == 1;
    }

    public boolean isPartOne(Account account){
        return accountDao.selectPartCntOne(account);
    }

    public boolean isPartTwo(Account account){
        return accountDao.selectPartCntTwo(account);
    } // 해당 회원 예금 계좌 또는 출금
}








