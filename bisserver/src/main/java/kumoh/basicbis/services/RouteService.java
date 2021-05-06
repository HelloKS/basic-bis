package kumoh.basicbis.services;

public class RouteService implements BaseService{
    public enum RES_Code{
        ACCEPT(1), FAIL(2);
        private final int value;
        RES_Code(int value){
            this.value = value;
        }
    }

    /*
    * 노선
    * */
    @Override
    public String processRequest(String reqText) {
        return null;
    }
}
