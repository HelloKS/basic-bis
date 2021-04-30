package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.MovieDAO;
import idol.tofu.server.persistence.MovieDTO;

import java.util.ArrayList;

public class MovieService {
    public enum Indicator {
        CODE(1), MOV_UID(2);

        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }
    // 각각의 요청에 대한 응답의 코드가 일치함.
    public enum Code {
        MOV_LIST_REQ(1), MOV_REQ1(2), MOV_REQ2(3), MOV_REQ3(4);  // MOV_REQ1: 특정 객체 요청, MOV_REQ2: 상영예정작 목록 요청, MOV_REQ3: 현재 상영작 목록 요청 (이름은 생각해서 바꾸겟겠음_)

        private final int value;

        Code(int value) {
            this.value = value;
        }
    }
    /*
        reqText example
        code 1: "ProtocolType,code"
        code 2: "ProtocolType,code,mov_uid"
        code 3: "ProtocolType,code"
        code 4: "ProtocolType,code"
     */
    public String processRequest(String reqText) {
        String result = null;
        MovieDAO movieDAO = new MovieDAO();
        StringBuilder funcResult = new StringBuilder();
        ArrayList<MovieDTO> list;
        int type = ProtocolType.MOV_RES.getType();
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];
        switch (code) {
            case MOV_LIST_REQ: {  //전체 영화 목록 요청
                list = movieDAO.getAllMovies();
                funcResult.append(list.size() + ",");
                for (MovieDTO movieDTO : list) {
                    funcResult.append(movieDTO.toString()).append(",");
                }
            }
            break;
            case MOV_REQ1: {  // 특정 영화 상세정보 요청
                funcResult.append(movieDAO.showMovieDetails(parsedText[Indicator.MOV_UID.value]).toString()).append(",");
            }
            break;
            case MOV_REQ2: {  // 상영 예정작 목록 요청
                list = movieDAO.showRunnableMovie();
                for (MovieDTO movieDTO : list) {
                    funcResult.append(movieDTO.toString()).append(",");
                }
            }
            break;
            case MOV_REQ3: {  // 현재 상영작 목록 요청
                list = movieDAO.showRunnigMovie();
                for (MovieDTO movieDTO : list) {
                    funcResult.append(movieDTO.toString()).append(",");
                }
            }
            break;
            default:
                break;
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = type + "," + code.value + "," + funcResult.toString();
        return result;
    }
}
