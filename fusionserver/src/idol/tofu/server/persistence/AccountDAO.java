package idol.tofu.server.persistence;

import oracle.jdbc.*;

import java.sql.*;
import java.util.ArrayList;

public class AccountDAO extends BasicDAOImpl {

    public AccountDAO() {
    }

    /*
    C가 입력한 회원정보 insert(회원가입) - 회원가입 시나리오
    사용자가 회원가입 창에서 입력한 정보들 DB에 insert.
     */
    public int insert(String id, String pw, String name, String nick, String phone, String email, String birth, String isAdmin) {
        String sql = "INSERT INTO \"accounts\" (MB_ID,MB_PWD,MB_NAME,MB_NICK,MB_PHONE,MB_EMAIL,MB_BIRTH,MB_ADMIN) values (?,?,?,?,?,?,?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();

            statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            statement.setString(2, pw);
            statement.setString(3, name);
            statement.setString(4, nick);
            statement.setString(5, phone);
            statement.setString(6, email);
            statement.setString(7, birth);
            statement.setString(8, isAdmin);

            result = statement.executeUpdate();


        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return result;
        }
    }

    /*
    아이디 중복 체크 기능 - 회원가입 시나리오
    S는 C가 입력한 id를 매개변수로 받고, accounts 테이블에서 해당 id 가 있는지 검사
    accounts 테이블에 해당 id 가 있으면 result.next()의 반환값 : true -> isDup = true 중복 되었다는 의미
    accounts 테이블에 해당 id 가 없으면 result.next()의 반환값 : false -> isDup = false 중복 되지 않았다는 의미
    */
    public boolean isDupId(String id) {
        String sql = "SELECT * FROM \"accounts\" WHERE MB_ID = ?";

        PreparedStatement statement = null;
        ResultSet result = null;

        boolean isDup = false;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, id);

            result = statement.executeQuery();

            if (result.next())
                isDup = true;

            if (result != null) result.close();
            if (conn != null) conn.close();
            if (statement != null) statement.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isDup;
    }

    /*
        닉네임 중복 체크 기능 - 회원가입 시나리오
        S는 C가 입력한 nick를 매개변수로 받고, accounts 테이블에서 해당 nick 가 있는지 검사
        accounts 테이블에 해당 nick 가 있으면 result.next()의 반환값 : true -> isDup = true 중복 되었다는 의미
        accounts 테이블에 해당 nick 가 없으면 result.next()의 반환값 : false -> isDup = false 중복 되지 않았다는 의미
    */
    public boolean isDupNick(String nick) {
        String sql = "SELECT * From \"accounts\" WHERE MB_NICK = ?";

        PreparedStatement statement = null;
        ResultSet result = null;

        boolean isDup = false;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, nick);

            result = statement.executeQuery();

            if (result.next())
                isDup = true;


        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }

            return isDup;
        }
    }

    /*
        로그인 성공여부 전송 - 로그인 시나리오
        S는 C가 입력한 id와 pwd를 매개변수로 받음,
        accounts 테이블에서 해당 id의 pwd를 검색,
        result.next() 가 true 이면, result pwd와 매개변수로 받은 pwd 가 일치하면 chk에 1대입
        pwd가 일치하지 않으면 2 대입
        result.next() 가 false 이면 chk 에 2 대입.
     */
    public int checkLogin(String id, String pwd) //1. 로그인 성공, 2. 로그인 실패
    {
        String sql = "SELECT MB_PWD FROM \"accounts\" WHERE MB_ID = ?";
        int chk = 0;
        PreparedStatement statement = null;
        getConnection();
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                if (result.getString("MB_PWD").equals(pwd))  //로그인 성공
                {
                    chk = 1;
                } else {  //로그인 실패
                    chk = 2;
                }
            } else { // 로그인 실패
                chk = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return chk;
    }

    /*
     * 관람등급 확인 단계
     * 사용자의 id를 통해 사용자 정보를 반환
     * */
    public AccountDTO getAccount(String id) {
        String sql = "SELECT * FROM \"accounts\" WHERE MB_ID = ?";
        AccountDTO account = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        getConnection();
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            result = statement.executeQuery();

            while(result.next()) {
                account = new AccountDTO();
                account.setId(result.getString("MB_ID"));
                account.setPassword(result.getString("MB_PWD"));
                account.setEmail(result.getString("MB_EMAIL"));
                account.setIsAdmin(result.getString("MB_ADMIN"));
                account.setBirth(result.getDate("MB_BIRTH"));
                account.setNickname(result.getString("MB_NICK"));
                account.setPhone(result.getString("MB_PHONE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (conn != null) conn.close();
                if (statement != null) statement.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return account;
    }

    /*
        관리자 여부 판단 - 로그인 시나리오
        클라이언트에게서 받은 id로 사용자의 관리자 여부를 확인한다.
     */
    public String isAdmin(String id) {
        String sql = "SELECT MB_ADMIN FROM \"accounts\" WHERE MB_ID = ?";
        PreparedStatement statement = null;
        ResultSet result = null;
        getConnection();
        String isAdmin = "F";
        try {
            statement = conn.prepareStatement(sql);
            statement.setString(1, id);
            result = statement.executeQuery();

            if (result.next()) {
                if (result.getString("MB_ADMIN").equals("T"))  //로그인 성공
                {
                    isAdmin = "T";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return isAdmin;
    }

    public String getMemberAge(String mb_id)
    {
        String sql = "SELECT TO_CHAR(MONTHS_BETWEEN(TRUNC(SYSDATE,'YEAR')," +
                "TRUNC(mb_birth,'YEAR')) /12 +1)" +
                "FROM \"accounts\" WHERE MB_ID = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String age ="";
        try{
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, mb_id);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                age = resultSet.getString("TO_CHAR(MONTHS_BETWEEN(TRUNC(SYSDATE,'YEAR'),TRUNC(MB_BIRTH,'YEAR'))/12+1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return age;
    }
}