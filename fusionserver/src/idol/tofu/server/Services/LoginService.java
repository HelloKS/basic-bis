package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.AccountDAO;

public class LoginService {

    public enum Indicator {
        CODE(1), ID(2), PWD(3);
        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }

    public enum Code {
        UNKNOWN(0),
        LOGIN(1), LOGOUT(2);
        private final int value;

        Code(int value) {
            this.value = value;
        }
    }

    /*
        reqText example
        code 1: "ProtocolType,code,id,pwd"
        code 2: "ProtocolType,code,id"
     */
    public String processRequest(String reqText) {
        String result = null;
        int logInResult;
        String isAdmin;
        int type = ProtocolType.LOG_RES.getType();
        //여기서 처리하고 result String 에 csv 형태로 전달
        String[] parsedText = reqText.split(",");
        Code code = Code.values()[Integer.parseInt(parsedText[Indicator.CODE.value])];
        switch (code) {
            case LOGIN: {
                System.out.println("[LoginService] 로그인 실행");
                AccountDAO accountDAO = new AccountDAO();
                logInResult = accountDAO.checkLogin(parsedText[Indicator.ID.value], parsedText[Indicator.PWD.value]); //로그인이 성공하면 1 반환, 로그인이 실패하면 2 반환
                isAdmin = accountDAO.isAdmin(parsedText[Indicator.ID.value]); //관리자이면 T 반환 아니면 F 반환
                result = type + "," + logInResult + "," + isAdmin;
            }
            break;
            case LOGOUT: {
                System.out.println("[LoginService] 로그아웃 실행");
                result = type + "," + parsedText[Indicator.ID.value];
            }
            break;
            default:
                break;
        }

        return result;
    }

}
