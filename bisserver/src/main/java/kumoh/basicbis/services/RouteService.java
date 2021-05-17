package kumoh.basicbis.services;

public class RouteService implements BaseService{
    /*
     *
     *
     * */
    //TODO : 구현해나가면서 필요한 CODE 작성
    public enum Indicator{
        CODE(1), TIMETABLE_UID(2);
        private final int value;
        Indicator(int value){
            this.value = value;
        }
    }
    public enum Code{
        TIMETABLE(1);
        private final int value;
        Code(int value){
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
