package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.*;

import java.util.ArrayList;

public class ManageService {
    public enum REQ_Code {
        THTR_ADD(1), THTR_MODIFY(2), THTR_DELETE(3),
        SCH_ADD(4), SCH_MODIFY(5), SCH_DELETE(6),
        SCR_ADD(7), SCR_MODIFY(8), SCR_DELETE(9),
        MOV_ADD(10), MOV_MODIFY(11), MOV_DELETE(12);

        private final int value;

        REQ_Code(int value) {
            this.value = value;
        }
    }

    /*
        code 1: "ProtocolType,code,thtr_name,thtr_addr,thtr_phone,thtr_accno,thtr_bank"
        code 2: "ProtocolType,code,thtr_uid,thtr_name,thtr_addr,thtr_phone"
        code 3: "ProtocolType,code,thtr_uid"

        code 4: "ProtocolType,code,mov_uid,scr_no,thtr_uid,sch_starttime"
        code 5: "ProtocolType,code,sch_uid,mov_no,scr_no,thtr_uid,sch_starttime"
        code 6: "ProtocolType,code,sch_uid"

        code 7: "ProtocolType,code,thtr_uid,scr_no,scr_rownum,scr_seatnum"
        code 8: "ProtocolType,code,thtr_uid,scr_no,scr_rownum,scr_seatnum"
        code 9: "ProtocolType,code,thtr_uid,scr_no"

        code 10: "ProtocolType,code,mov_name,thtr_actor,mov_rating,mov_genre,mov_date,mov_price,mov_runtime,mov_detail,mov_tralier,mov_poster"
        code 11: "ProtocolType,code,mov_uid,mov_actor,mov_rating,mov_genre,mov_date,mov_price,mov_runtime,mov_detail,mov_tralier,mov_poster"
        code 12: "Protocoltype,code,mov_uid"
     */
    public String processRequest(String reqText) {
        String result = null;
        int funcResult = 0;
        int type = ProtocolType.MANAGE_RES.getType();
        String[] parsedText = reqText.split(",");
        REQ_Code req_code = REQ_Code.values()[Integer.parseInt(parsedText[1])];
        int res_code;
        switch (req_code) {
            case THTR_ADD: {
                TheaterDAO theaterDAO = new TheaterDAO();
                funcResult = theaterDAO.insert(parsedText[2], parsedText[3], parsedText[4], parsedText[5], parsedText[6]);
            }
            break;
            case THTR_MODIFY: {
                TheaterDAO theaterDAO = new TheaterDAO();
                funcResult = theaterDAO.update(parsedText[2], parsedText[3], parsedText[4], parsedText[5]);
            }
            break;
            case THTR_DELETE: {
                TheaterDAO theaterDAO = new TheaterDAO();
                funcResult = theaterDAO.delete(parsedText[2]);
            }
            break;
            case SCH_ADD: {
                ScheduleDAO scheduleDAO = new ScheduleDAO();
                funcResult = scheduleDAO.insert(parsedText[2], parsedText[3], parsedText[4], parsedText[5]);
            }
            break;
            case SCH_MODIFY: {
                ScheduleDAO scheduleDAO = new ScheduleDAO();
                funcResult = scheduleDAO.update(parsedText[2], parsedText[3]);
            }
            break;
            case SCH_DELETE: {
                ScheduleDAO scheduleDAO = new ScheduleDAO();
                funcResult = scheduleDAO.delete(Integer.parseInt(parsedText[2]));
            }
            break;
            case SCR_ADD: {
                ScreenDAO screenDAO = new ScreenDAO();
                funcResult = screenDAO.insert(parsedText[2], parsedText[3], parsedText[4], parsedText[5]);
            }
            break;
            case SCR_MODIFY: {
                ScreenDAO screenDAO = new ScreenDAO();
                funcResult = screenDAO.update(parsedText[2], parsedText[3], parsedText[4], parsedText[5]);
            }
            break;
            case SCR_DELETE: {
                ScreenDAO screenDAO = new ScreenDAO();
                funcResult = screenDAO.delete(parsedText[2], parsedText[3]);
            }
            break;
            case MOV_ADD: {
                MovieDAO movieDAO = new MovieDAO();
                funcResult = movieDAO.insert(parsedText[2], parsedText[3], parsedText[4], parsedText[5], parsedText[6], parsedText[7], parsedText[8], parsedText[9], parsedText[10], parsedText[11], parsedText[12]);
            }
            break;
            case MOV_MODIFY: {
                MovieDAO movieDAO = new MovieDAO();
                funcResult = movieDAO.update(parsedText[2], parsedText[3], parsedText[4], parsedText[5], parsedText[6], parsedText[7], parsedText[8], parsedText[9], parsedText[10], parsedText[11], parsedText[12], parsedText[13]);
            }
            break;
            case MOV_DELETE: {
                MovieDAO movieDAO = new MovieDAO();
                funcResult = movieDAO.delete(parsedText[2]);
            }
            break;
            default:
                break;
        }
        if (funcResult == 1) {
            res_code = 1;
        } else {
            res_code = 2;
        }
        result = type + "," + res_code;
        return result;
    }

}
