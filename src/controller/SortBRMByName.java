package controller;

import model.BRM;

import java.util.Comparator;

public class SortBRMByName implements Comparator <BRM> {
    @Override
    public int compare(BRM o1, BRM o2) {
        String name1 = o1.getReader().getReaderName().
                substring(o1.getReader().getReaderName().lastIndexOf(" "));
        String name2 = o2.getReader().getReaderName().
                substring(o2.getReader().getReaderName().lastIndexOf(" "));
        return name1.compareTo(name2);
    }
}
