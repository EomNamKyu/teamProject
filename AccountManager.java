package teamProject;

import java.util.Scanner;

public class AccountManager {
    private final Scanner stdIn;
    private final AccountDao accountDao;

    public AccountManager() {
        stdIn = new Scanner(System.in);
        accountDao = new AccountDao();
    }

    void addMember(){
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
    boolean plusMember(Member member) {
        if (this.isMember(member.getMember_id())) {
            System.out.println(member.getMember_id() + " 존재하는 아이디입니다.");
            return false;
        }
        return accountDao.insertMember(member);
    }
    void addAccount(){
        Account account = new Account();

        System.out.print("아이디 : ");
        account.setMember_id(stdIn.next());

        System.out.print("이름 : ");
        account.setName(stdIn.next());

        System.out.print("나이 : ");
        account.setAge(stdIn.nextInt());

        System.out.print("거주지 : ");
        account.setAddress(stdIn.next());

        System.out.print("거주지 : ");
        account.setAddress(stdIn.next());

        System.out.print("거주지 : ");
        account.setAddress(stdIn.next());

        if (this.plusMember(member)) {
            System.out.println("아이디가 생성되었습니다.");
        } else {
            System.out.println("아이디 생성에 실패했습니다.");
        }
    } // 계좌 개설
//    void deposit(); // 입금 처리
//    void withdraw(); // 출금 처리
//    void viewHistory(); // 조회 처리
//    void disConnect(); // 종료 처리
    boolean isMember(String member_id) {
        return accountDao.selectMemberIdCnt(member_id) == 1;
    };
    boolean isAccount(String account_number) {
        return accountDao.selectAccountIdCnt(account_number) == 1;
    };
    boolean isPart(Account account) //

}
