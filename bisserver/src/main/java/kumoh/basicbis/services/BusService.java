package kumoh.basicbis.services;

import kumoh.basicbis.persistence.BusDAO;
import kumoh.basicbis.persistence.BusDTO;

import java.util.ArrayList;

public class BusService implements BaseService{

    public enum RES_Code{
        ACCEPT(1), FAIL(2);
        private final int value;
        RES_Code(int value){
            this.value = value;
        }
    }


    @Override
    public String processRequest(String reqText) {
        String result = null;
        StringBuilder funcResult = new StringBuilder();
        BusDAO busDAO = new BusDAO();
        ArrayList<BusDTO> list = null;

        return null;
    }
}
