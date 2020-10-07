package controller;

import com.sun.xml.internal.ws.util.xml.CDATA;
import model.BRM;

import java.util.ArrayList;
import java.util.Collections;

public class OtherControl {
    public ArrayList <BRM> updateBRM(ArrayList <BRM> brms, BRM brm) {
        boolean isAvailable = false;
        for (int i = 0; i < brms.size(); i++) {
            BRM brm1 = brms.get(i);
            if (brm1.getReader().getReaderID() == brm.getReader().getReaderID() &&
                    brm1.getBook().getBookID() == brm.getBook().getBookID()) {
                isAvailable = true;
                brms.set(i, brm);
            }
        }
        if (!isAvailable) {
            brms.add(brm);
        }
        return brms;
    }

    public ArrayList <BRM> searchReaderByName(ArrayList <BRM> brms, String key){
        String pattern = ".*" + key.toLowerCase() + ".*";
        ArrayList <BRM> list = new ArrayList<>();
        for(int i = 0; i<brms.size(); i++){
            BRM brm = brms.get(i);
            if(brm.getReader().getReaderName().toLowerCase().matches(pattern))
                list.add(brm);
        }
        return list;
    }
}
