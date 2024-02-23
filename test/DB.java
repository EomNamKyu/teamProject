package teamProject.test;

import java.sql.*;

public class DB {
    // 사용할 객체를 위한 참조변수 선언. 클래스내의 여러 메서드에서 사용해야 되서 인스턴스 변수로 선언
    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null; // 쿼리시에 결과를 저장하는 용도로 사용 select 에 주로 사용

    public void connectDB(){
        // 데이터 베이스 연결
        // 데이터 베이스 접속 정보
        final String driver = "org.mariadb.jdbc.Driver";
        final String DB_HOST = "127.0.0.1";
        final String DB_PORT = "3306";
        final String DB_NAME = "sample"; // 데이터 베이스 이름
        final String DB_URL = "jdbc:mariadb://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
        final String DB_USER = "root";
        final String DB_PASS = "7759";

        // 디비 접속
        try {
            Class.forName(driver); // JDBC 드라이버 등록.
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS); // 디비 연결
            if (conn != null){
                System.out.println("DB 접속 성공");
            }
        }catch (ClassNotFoundException e){ // JDBC 드라이버 등록 실패시
            System.out.println("드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) { // DriverManager.getConnection() 실패시
            System.out.println("DB 접속 실패");
            throw new RuntimeException(e);
        }
    }

    public void closeDB(){
        // 데이터 베이스 연결 해제
        try {
            if (resultSet != null){ // 열린 경우 닫음
                resultSet.close();
            }
            if (preparedStatement != null){ // 열린 경우 닫음
                preparedStatement.close();
            }
            if (conn != null && !conn.isClosed()){
                conn.close();
                System.out.println("DB 접속 해제");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

