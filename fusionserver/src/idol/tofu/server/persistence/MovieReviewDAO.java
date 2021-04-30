package idol.tofu.server.persistence;

import oracle.jdbc.*;

import java.sql.*;
import java.util.ArrayList;

public class MovieReviewDAO extends BasicDAOImpl {
    public MovieReviewDAO() {
    }

    /*
        리뷰 작성 요청 - 영화 리뷰 작성 시나리오
        C가 S에게 리뷰를 작성한 멤버의 id와 작성한 영화의 uid 리뷰의 title, story, mark를 전송하면서 리뷰작성을 요청
        S는 C가 보낸 정보를 매개변수로 받아 DB에 추가함.
        insert 함수는 1회 호출에 1개의 row만 추가하므로 sql문 성공 시 반드시 1을 반환
        sql문 실패시 0 반환
     */
    public int insert(String mbId, String movUid, String title, String story, String mark) {
        String sql = "INSERT INTO \"movie_review\" (RVW_ID, MB_ID, MOV_UID, RVW_TITLE, RVW_STORY, RVW_MARK) values (RVW_ID_PK.NEXTVAL,?,?,?,?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, mbId);
            statement.setString(2, movUid);
            statement.setString(3, title);
            statement.setString(4, story);
            statement.setString(5, mark);

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
        }
        return result;
    }

    /*
        특정영화 리뷰 전체 요청 - 영화 리뷰 작성, 영화 리뷰 수정, 영화 리뷰 삭제 시나리오
        C는 S에게 특정 영화의 uid를 전송하면서 해당 영화의 전체 리뷰목록을 요청함.
        S는 C에게 영화의uid를 매개변수로 받음
        sql 문을 검색한 결과를 ResultSet에 삽입 후 DTO에 해당 정보를 받아 ArrayList에 삽입
     */
    public ArrayList<MovieReviewDTO> showReview(String movUid) {
        ArrayList<MovieReviewDTO> list = new ArrayList<MovieReviewDTO>();

        String sql = "SELECT * FROM \"movie_review\" WHERE MOV_UID = ?";

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, movUid);
            result = statement.executeQuery();

            while (result.next()) {
                MovieReviewDTO movieReviewDTO = new MovieReviewDTO();
                movieReviewDTO.setUserId(result.getString("MB_ID"));
                movieReviewDTO.setMovieUid(result.getInt("MOV_UID"));
                movieReviewDTO.setTitle(result.getString("RVW_TITLE"));
                movieReviewDTO.setStory(result.getString("RVW_STORY"));
                movieReviewDTO.setMark(result.getInt("RVW_MARK"));
                list.add(movieReviewDTO);
            }
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
        }
        return list;
    }

    /*
        리뷰 작성 요청 - 영화 리뷰 작성 시나리오
        C가 S에게 리뷰를 작성한 멤버의 id와 수정한 영화의 uid 리뷰의 title, story, mark 를 전송하면서 리뷰수정을 요청
        S는 C가 보낸 정보를 매개변수로 받아 DB에 UPDATE 함.
        eidtReview 함수는 1회 호출에 1개의 row만 추가하므로 sql문 성공 시 반드시 1을 반환
        sql문 실패시 0 반환
    */
    public int editReview(String rvwId, String title, String story, String mark) {
        String sql = "UPDATE \"movie_review\" SET RVW_TITLE = ?, RVW_STORY = ?, RVW_MARK = ? WHERE RVW_ID = ?";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);

            statement.setString(1, title);
            statement.setString(2, story);
            statement.setString(3, mark);
            statement.setString(4, rvwId);

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
        }
        return result;
    }

    /*
        리뷰 삭제 요청 - 영화 리뷰 삭제 시나리오
        C가 해당 리뷰의 ID를 전송하면서 리뷰의 삭제 요청
        S는 C가 전송한 ID를 매개변수로 받고 movie_review에서 delete 수행
        deleteReview 함수는 1회 호출에 1개의 row만 추가하므로 sql문 성공 시 반드시 1을 반환
        sql문 실패시 0 반환
     */
    public int deleteReivew(String rvwId) {
        String sql = "DELETE FROM \"movie_review\" WHERE RVW_ID = ?";
        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, rvwId);

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
        }
        return result;
    }
}
