package idol.tofu.client;

import idol.tofu.client.persistence.Session;

public class SessionManager {

    /*
    SessionManager
    본 어플리케이션 상에 계정정보를 관리하는 Manager.
    Login 여부 및 Session 객체 (ID, PW를 포함) 보관 및 제공
    모든 로그인 확인 관련은 본 SessionManager를 통해야 함!
     */

    private static Session globalSession = null;

    public static Session getSession() {
        return globalSession;
    }

    public static void setSession(Session session) {
        globalSession = session;
    }

    public static void deleteSession() {
        globalSession = null;
    }

    public static boolean isLogin() {
        if (globalSession != null) {
            return true;
        } else {
            return false;
        }
    }
}
