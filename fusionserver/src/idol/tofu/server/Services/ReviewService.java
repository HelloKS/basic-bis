package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.MovieReviewDAO;
import idol.tofu.server.persistence.MovieReviewDTO;

import java.util.ArrayList;

public class ReviewService {
    public enum Indicator {
        CODE(1),
        MOV_UID_LIST(2), MOV_UID_WRITE(3),
        MEM_ID(2),
        RVW_TITLE_WRITE(4), RVW_TITLE_EDIT(3),
        RVW_STORY_WRITE(5), RVW_STORY_EDIT(4),
        RVW_MARK_WRITE(6), RVW_MARK_EDIT(5),
        RVW_UID(2);

        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }

    public enum REQ_Code {
        RVW_LIST_REQ(1), RVW_WRITE_REQ(2), RVW_EDIT_REQ(3), RVW_DELETE_REQ(4);

        private final int value;

        REQ_Code(int value) {
            this.value = value;
        }
    }
    /*
        write, eidt, delete 요청에 대한 응답의 코드가 성공 or 실패로 되어 있어서 enum 을 만들어줌.
     */
    public enum RES_Code {
        RVW_LIST_RES(1), RVW_RES_SUCCESS(2), RVW_RES_FAIL(3);

        private final int value;

        RES_Code(int value) {
            this.value = value;
        }
    }

    /*
        reqText example
        code 1: "ProtocolType,code,mov_uid"
        code 2: "ProtocolType,code,mem_id,mov_uid,rvw_title,rvw_story,rvw_mark"
        code 3: "ProtocolType,code,rvw_uid,rvw_title,rvw_story,rvw_mark"
        code 4: "ProtocolType,code,rvw_uid"
     */
    public String processRequest(String reqText) {
        String result = null;
        MovieReviewDAO movieReviewDAO = new MovieReviewDAO();
        StringBuilder funcResult = new StringBuilder();
        ArrayList<MovieReviewDTO> list;
        int type = ProtocolType.MOV_RES.getType();
        String[] parsedText = reqText.split(",");
        REQ_Code req_code = REQ_Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];
        int res_code = 0;
        switch (req_code) {
            case RVW_LIST_REQ: {  // 해당 영화의 리뷰 목록 요청
                res_code = RES_Code.RVW_LIST_RES.value;
                list = movieReviewDAO.showReview(parsedText[Indicator.MOV_UID_LIST.value]);
                funcResult.append(res_code).append(",");
                for (MovieReviewDTO movieReviewDTO : list) {
                    funcResult.append(movieReviewDTO.toString()).append(",");
                }
            }
            break;
            case RVW_WRITE_REQ: {  // 리뷰 작성 요청
                int writeResult = movieReviewDAO.insert(parsedText[Indicator.MEM_ID.value], parsedText[Indicator.MOV_UID_WRITE.value],
                        parsedText[Indicator.RVW_TITLE_WRITE.value], parsedText[Indicator.RVW_STORY_WRITE.value],
                        parsedText[Indicator.RVW_MARK_WRITE.value]);
                if (writeResult == 1) {
                    res_code = RES_Code.RVW_RES_SUCCESS.value;
                } else {
                    res_code = RES_Code.RVW_RES_FAIL.value;
                }
                funcResult.append(res_code).append(",");
            }
            break;
            case RVW_EDIT_REQ: {  // 리뷰 수정 요청
                int editResult = movieReviewDAO.editReview(parsedText[Indicator.RVW_UID.value], parsedText[Indicator.RVW_TITLE_EDIT.value],
                        parsedText[Indicator.RVW_STORY_EDIT.value], parsedText[Indicator.RVW_MARK_EDIT.value]);
                if (editResult == 1) {
                    res_code = RES_Code.RVW_RES_SUCCESS.value;
                } else {
                    res_code = RES_Code.RVW_RES_FAIL.value;
                }
                funcResult.append(res_code).append(",");
            }
            break;
            case RVW_DELETE_REQ: {  // 리뷰 삭제 요청
                int delResult = movieReviewDAO.deleteReivew(parsedText[Indicator.RVW_UID.value]);
                if (delResult == 1) {
                    res_code = RES_Code.RVW_RES_SUCCESS.value;
                } else {
                    res_code = RES_Code.RVW_RES_FAIL.value;
                }
                funcResult.append(res_code).append(",");
            }
            break;
            default:
                break;
        }
        funcResult.deleteCharAt(funcResult.lastIndexOf(","));
        result = type + "," + funcResult.toString();
        return result;
    }
}
