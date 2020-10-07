package model;

public class Reader {
    private int readerID;
    private String readerName, address, phoneNum;
    private static int rID = 10000000;

    public Reader() {
    }

    public Reader(int readerID) {
        this.readerID = readerID;
    }

    public Reader(int readerID, String readerName, String address, String phoneNum) {
        this.readerID = readerID;
        this.readerName = readerName;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public int getReaderID() {
        return readerID;
    }

    public void setReaderID(int readerID) {
        this.readerID = readerID;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public static int getrID() {
        return rID;
    }

    public static void setrID(int rID) {
        Reader.rID = rID;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "readerID=" + readerID +
                ", readerName='" + readerName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
