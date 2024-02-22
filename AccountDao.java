package teamProject;

import java.sql.*;
import java.util.ArrayList;

public class AccountDao {
    private Connection connection = null;

    AccountDao() {getConnection();}
    private void getConnection() { // 디비 연결 생성자에서 실행
        try {
            String url = "jdbc:mariadb://localhost:3306/team_project";
            String user = "root";
            String password = "4532";

            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // 데이터 베이스 연결
    boolean insertMember(Member member) {
        String sql = "INSERT INTO team_project.member VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
           // preparedStatement.setInt(1, member.getId()); // 추가 할 때 자동으로 생성되어서 생략 가능
            preparedStatement.setString(1, member.getMember_id());
            preparedStatement.setString(2, member.getName());
            preparedStatement.setInt(3, member.getAge());
            preparedStatement.setString(4, member.getAddress());

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } // 회원 등록 디비 처리
    boolean insertAccount(Account account) {
        String sql = "INSERT INTO team_project.account VALUES (?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
           // preparedStatement.setInt(1, account.getId());  // 추가 할 때 자동으로 생성되어서 생략 가능
            preparedStatement.setString(1, account.getMember_id());
            preparedStatement.setInt(2, account.getAccount_type());
            preparedStatement.setString(3, account.getAccount_number());
            preparedStatement.setDouble(4, account.getBalance());
            preparedStatement.setDouble(5, account.getInterest_rate());
            preparedStatement.setDouble(6, account.getFee_rate());

            int result = preparedStatement.executeUpdate(); // executeUpdate() 는 select 제외 성공된 행의수 반환 수행된 행의수를 result에 저장
            if (result > 0){
                return true;// 성공적으로 반환을 했다면 6이므로 true 가 되고 메서드 종료
            }else {
                System.out.println("계정을 생성하는데 실패했습니다.");
            }
           // return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
           // throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false; // 그게 아니라면 catch 가 실행 되고 false 로 메서드 종료
    } // 계좌 개설 디비 처리
    void insertAccountHistory(AccountHistory accountHistory){
        String sql = "INSERT INTO insertAccountHistory (account_number, transaction_type, transaction_amount, balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, accountHistory.getAccount_number());
            preparedStatement.setString(2, accountHistory.getTransaction_type());
            preparedStatement.setDouble(3, accountHistory.getTransaction_amount());
            preparedStatement.setDouble(4, accountHistory.getBalance());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }  // 거래 내역 저장
    ArrayList<AccountHistory> selectAccountHistories(String accountId){} // 특정 계좌 번호에 대한 거래 내역 조회
    void disConnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // 데이터 베이스 연결 해제
    Account selectAccount(String account_number) {
        Account account = null;
        String sql = "SELECT * FROM account WHERE id= '" + account_number + "'";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,account_number);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    account = new Account();
                   // account.setId(resultSet.getInt("id")); // 자동생성이니 필요없지 않을까
                    account.setMember_id(resultSet.getString("member_id"));
                    account.setAccount_type(resultSet.getInt("account_type"));
                    account.setAccount_number(resultSet.getString("account_number"));
                    account.setBalance(resultSet.getDouble("balance"));
                    account.setInterest_rate(resultSet.getDouble("interest_rate"));
                    account.setFee_rate(resultSet.getDouble("fee_rate"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    } // 특정 계좌 번호의 정보를 가져옴
    double selectBalance(String account_number) {
        Account account = null;
        double wantBalance = 0;
        String sql = "SELECT balance FROM account WHERE account_number = '" + account_number + "'";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,account_number);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    account = new Account();
                    account.setBalance(resultSet.getDouble("balance"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        wantBalance = account.getBalance();
        return wantBalance;
    } // 특정 계좌 번호의 잔액을 가져옴
    void updateBalance(String account_number, double balance, boolean flag) {
        String sql;
        int cnt = 0;
        if (flag) {
            sql = String.format("UPDATE account SET balance = balance + ? WHERE (account_number = ?)");
        } else {
            sql = String.format("UPDATE account SET balance = balance - ? WHERE (account_number = ?)");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, account_number);
            preparedStatement.setDouble(2, balance);

            cnt = preparedStatement.executeUpdate();
            if (cnt != 1) {
                System.out.println("입출금을 실패하였습니다.");
            } else {
                System.out.println("입출금을 성공하였습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } // 특정 계좌 번호의 잔액을 수정
    int selectAccountIdCnt(String account_number) {
        String sql = "SELECT COUNT(*) AS cnt FROM team_project.account WHERE account_number = ?";
        int cnt;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,account_number);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                cnt = resultSet.getInt(1);
                }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cnt;
    } // 해당 계좌번호의 계좌 개수를 반환
    int selectMemberIdCnt(String member_id) {
        String sql = "SELECT COUNT(*) AS cnt FROM team_project.member WHERE member_id = ?";
        int cnt;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,member_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                cnt = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cnt;
    } // 해당 아이디의 회원 개수를 반환

    }




