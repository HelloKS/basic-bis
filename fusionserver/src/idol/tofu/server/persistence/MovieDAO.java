package idol.tofu.server.persistence;

import oracle.jdbc.*;
import oracle.jdbc.proxy.annotation.GetCreator;

import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;

public class MovieDAO extends BasicDAOImpl {

    public MovieDAO() {
    }

    /*
        영화 추가 기능 - 관리자의 영화 추가 시나리오
        사용자(관리자)가 입력한 영화에 대한 정보를 movies 테이블에 insert
        insert 함수는 1회 호출에 1개의 row만 추가하므로 sql문 성공 시 반드시 1을 반환
        sql문 실패시 0 반환
     */
    public int insert(String name, String actor, String rating, String genre, String date, String price, String runtime, String detail, String trailer, String poster, String status) {
        String sql = "INSERT INTO \"movies\" (MOV_UID, MOV_NAME, MOV_ACTOR, MOV_RATING, MOV_GENRE, MOV_DATE, MOV_PRICE, MOV_RUNTIME, MOV_STATUS, MOV_DETAIL, MOV_TRAILER, MOV_POSTER)" +
                "values (MOV_UID_PK.NEXTVAL,?,?,?,?,TO_DATE(?,'YYYYMMDD'),?,?,?,?,?,?)";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, actor);
            statement.setString(3, rating);
            statement.setString(4, genre);
            statement.setString(5, date);
            statement.setString(6, price);
            statement.setString(7, runtime);
            statement.setString(8, status);
            statement.setString(9, detail);
            statement.setString(10, trailer);
            statement.setString(11, poster);

            result = statement.executeUpdate();

        } catch (SQLException se) {
            se.printStackTrace();
        }finally {
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
     * 관리자 기능
     * 영화 관리 시나리오
     * 영화 관리 UI에서 전체 영화에 대한 정보가 필요
     * */
    public ArrayList<MovieDTO> getAllMovies() {
        String sql = "SELECT * FROM \"movies\"";
        ArrayList<MovieDTO> list = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
                MovieDTO movie = new MovieDTO();
                movie.setUid(result.getInt("MOV_UID"));
                movie.setName(result.getString("MOV_NAME"));
                movie.setActor(result.getString("MOV_ACTOR"));
                movie.setRating(result.getInt("MOV_RATING"));
                movie.setGenre(result.getString("MOV_GENRE"));
                movie.setReleaseDate((result.getDate("MOV_DATE")));
                movie.setPrice((result.getInt("MOV_PRICE")));
                movie.setRuntime((result.getInt("MOV_RUNTIME")));
                movie.setStatus((result.getString("MOV_STATUS")));
                movie.setDetail((result.getString("MOV_DETAIL")));
                movie.setTrailer((result.getString("MOV_TRAILER")));
                movie.setPoster((result.getString("MOV_POSTER")));
                list.add(movie);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return list;
        }
    }

    /*
     현재 상영작 전체 목록 검색 - 예매시나리오 및 영화 시나리오 - 현재 상영작 전체목록
     moives테이블에서 MOV_STATUS 가 'T' 인 MOV_NAME 을 검색후 결과를 ResultSet에 저장 후
     해당 개체를 DTO에 저장 후 ArrayList에 넣음
     moives테이블에서 MOV_STATUS 가 'T' 인 MOV_NAME 을 검색
    */
    public ArrayList<MovieDTO> showRunnigMovie() {
        ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();
        String sql = "SELECT MOV_NAME FROM \"movies\" WHERE MOV_STATUS = 'T'";
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                MovieDTO movie = new MovieDTO();
                movie.setName(result.getString("MOV_NAME"));
                //movie.setActor(result.getString("MOV_ACTOR"));
                list.add(movie);
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
        finally {
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
        현재 상영중인 영화를 상영중지 - 관리자 영화 수정 시나리오(? 확실하지 않으니 확실하게 아는분 알려주세요..)
        S는 C에게 영화 고유번호를 매개변수로 받음
        해당 고유번호를 갖고 있는 레코드의 MOV_STATUS 를 F로 UPDATE
        stopScreening 함수는 1회 호출에 1개의 row만 UPDATE 하므로 sql문 성공 시 반드시 1을 반환
        sql문 실패시 0 반환
    */
    public int stopScreening(String mvUid) {
        String sql = "UPDATE \"movies\" SET MOV_STATUS = 'F' WHERE MOV_UID = ?";
        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, mvUid);
            result = statement.executeUpdate();
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
        상영 예정작 목록 조회 - 상영 예정작 전체 목록 조회 시나리오
        movies 테이블의 모든 레코드 들 중 MOV_DATE가 SYSDATE(현재시각) 을 넘어서는 영화(상영예정작)를 검색.
        select로 mov_name과 mov_actor를 검색 후 결과는 ResultSet에 저장, MovieDTO로 해당 객체의 정보 저장 후 ArrayList에 저장
        ArrayList 반환
     */
    public ArrayList<MovieDTO> showRunnableMovie() {
        ArrayList<MovieDTO> list = new ArrayList<MovieDTO>();
        String sql = "SELECT MOV_NAME, MOV_ACTOR FROM \"movies\" WHERE MOV_DATE > SYSDATE";

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                MovieDTO movie = new MovieDTO();
                movie.setName(result.getString("MOV_NAME"));
                movie.setActor(result.getString("MOV_ACTOR"));
                list.add(movie);
            }
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return list;
        }
    }

    /*
         특정영화 상세정보 - 특정 영화 상세정보 시나리오
         S는 C에게 mov_uid를 매개변수로 받음
         S는 DB에서 mov_uid를 검색해서 해당 레코드의 정보를 모두 ResultSet에 저장
         후 DTO를 생성하여 해당 개체의 정보를 담고 ArrayList에 넣고 반환

         결제 시나리오
         C에게 결제금액을 전송하기 위해 영화의 가격을 알기위해 사용용
     */
    public MovieDTO showMovieDetails(String mvUid) {
        String sql = "SELECT * From \"movies\" WHERE MOV_UID = ?";

        PreparedStatement statement = null;
        ResultSet result = null;
        MovieDTO movie = new MovieDTO();
        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, mvUid);
            result = statement.executeQuery();

            while(result.next()) {
                movie.setUid(result.getInt("MOV_UID"));
                movie.setName(result.getString("MOV_NAME"));
                movie.setActor(result.getString("MOV_ACTOR"));
                movie.setRating(result.getInt("MOV_RATING"));
                movie.setGenre(result.getString("MOV_GENRE"));
                movie.setReleaseDate((result.getDate("MOV_DATE")));
                movie.setPrice((result.getInt("MOV_PRICE")));
                movie.setRuntime((result.getInt("MOV_RUNTIME")));
                movie.setStatus((result.getString("MOV_STATUS")));
                movie.setDetail((result.getString("MOV_DETAIL")));
                movie.setTrailer((result.getString("MOV_TRAILER")));
                movie.setPoster((result.getString("MOV_POSTER")));
            }
        } finally {
            try {
                if (result != null) result.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return movie;
        }
    }

    public int update(String mov_uid, String name, String actor, String rating, String genre,
                      String date, String price, String runtime, String detail, String trailer, String poster, String status) {
        String sql = "UPDATE \"movies\" SET MOV_NAME = ?, MOV_ACTOR = ?, MOV_RATING = ?, MOV_GENRE = ?," +
                " MOV_DATE = TO_DATE(?,'YYYYMMDD'), MOV_PRICE = ?, MOV_RUNTIME = ?, MOV_DETAIL = ?, MOV_TRAILER = ?, MOV_POSTER = ?, MOV_STATUS = ? WHERE MOV_UID = ?";

        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, actor);
            statement.setString(3, rating);
            statement.setString(4, genre);
            statement.setString(5, date);
            statement.setString(6, price);
            statement.setString(7, runtime);
            statement.setString(8, detail);
            statement.setString(9, trailer);
            statement.setString(10, poster);
            statement.setString(11, status);
            statement.setString(12, mov_uid);

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

    public int delete(String mov_uid) {
        String sql = "DELETE FROM \"movies\" WHERE MOV_UID = ?";
        PreparedStatement statement = null;
        int result = 0;

        try {
            getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, mov_uid);

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

