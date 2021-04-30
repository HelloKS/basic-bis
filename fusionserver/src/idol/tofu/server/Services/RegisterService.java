package idol.tofu.server.Services;

import idol.tofu.server.ProtocolType;
import idol.tofu.server.persistence.AccountDAO;

public class RegisterService {
    public enum Indicator {
        ID(1), PWD(2),
        NAME(3), NICK(4),
        PHONE(5), EMAIL(6),
        BIRTH(7), ISADMIN(8);

        private final int value;

        Indicator(int value) {
            this.value = value;
        }
    }
    /*
        reqText example
        code 1: "ProtocolType,code,id,pwd,name,nick,phone,email,birth,isAdmin"
     */
    public String processRequest(String reqText) {
        String result = null;
        boolean isDupId;
        boolean isDupNick;
        int regResult;
        String[] parsedText = reqText.split(",");
        AccountDAO accountDAO = new AccountDAO();
        isDupId = accountDAO.isDupId(parsedText[Indicator.ID.value]);
        isDupNick = accountDAO.isDupNick((parsedText[Indicator.NICK.value]));
        if(isDupId || isDupNick) {
            regResult = 2;
        }
        else {
            regResult = accountDAO.insert(parsedText[Indicator.ID.value], parsedText[Indicator.PWD.value],
                    parsedText[Indicator.NAME.value], parsedText[Indicator.NICK.value],
                    parsedText[Indicator.PHONE.value], parsedText[Indicator.EMAIL.value],
                    parsedText[Indicator.BIRTH.value], parsedText[Indicator.ISADMIN.value]);
        }
        if (regResult != 1) {
            regResult = 2;
        }
        int type = ProtocolType.REG_RES.getType();
        result = type + "," + regResult;

        return result;
    }
}
