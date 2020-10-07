package controller;

import model.BRM;
import model.Book;
import model.Reader;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DataControl {
    /*
    ghi thông tin sách, bạn đọc, BRM vào file

    đọc thông tin từ file -> chuyển thành đối tượng sách/ bạn đọc/ BRM
                          -> thêm vào danh sách file
                          -> trả về danh sách
     */
    private FileWriter fileWriter;
    private PrintWriter printWriter;
    private BufferedWriter bufferedWriter;
    private Scanner scanner;
    public void openFileToWrite(String fileName){
        try {
            fileWriter = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFileAfterWrite(String fileName){
        try {
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBooksToFile(Book book, String fileName){
        openFileToWrite(fileName);

        //int bookID, String bookName, String author, String spec, int publishYear, int quantity

        printWriter.println(book.getBookID() + "|" + book.getBookName() + "|" + book.getAuthor()
                + "|" + book.getSpec() + "|" + book.getPublishYear() + "|" + book.getQuantity());
        closeFileAfterWrite(fileName);
    }

    public void writeReadersToFile(Reader reader, String fileName){
        openFileToWrite(fileName);

        //int readerID, String readerName, String address, String phoneNum

        printWriter.println(reader.getReaderID() + "|" + reader.getReaderName() + "|" + reader.getAddress()
                + "|" + reader.getPhoneNum());
        closeFileAfterWrite(fileName);
    }

    /*public void writeBRMToFile(BRM brm, String fileName){
        openFileToWrite(fileName);
        printWriter.println(brm.getBook().getBookID() + "|" + brm.getReader().getReaderID() +
                "|" + brm.getBorrowNumber() + "|" + brm.getStatus());
        closeFileAfterWrite(fileName);
    }*/

    public void openFileToRead(String fileName){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            scanner = new Scanner(Paths.get(fileName), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeFileAfterRead(String fileName){
        scanner.close();
    }

    public ArrayList<Book> readBooksInFile(String fileName){
        openFileToRead(fileName);
        ArrayList<Book> books = new ArrayList<>();
        while (scanner.hasNextLine()){
            String data = scanner.nextLine();
            Book book = convertToBook(data);
            books.add(book);
        }
        closeFileAfterRead(fileName);
        return books;
    }

    private Book convertToBook(String data) {

        //int bookID, String bookName, String author, String spec, int publishYear, int quantity

        String datas[] = data.split("[|]");
        Book book = new Book(Integer.parseInt(datas[0]), datas[1], datas[2],
                datas[3], Integer.parseInt(datas[4]), Integer.parseInt(datas[5]));
        return book;
    }

    public ArrayList<Reader> readReadersInFile(String fileName){
        openFileToRead(fileName);
        ArrayList<Reader> readers = new ArrayList<>();
        while(scanner.hasNextLine()){
            String data = scanner.nextLine();
            Reader reader = convertToReader(data);
            readers.add(reader);
        }
        closeFileAfterRead(fileName);
        return readers;
    }

    private Reader convertToReader(String data){

        //int readerID, String readerName, String address, String phoneNum

        String datas[] = data.split("[|]");
        Reader reader = new Reader(Integer.parseInt(datas[0]), datas[1], datas[2], datas[3]);
        return reader;
    }

    public ArrayList<BRM> readBRMsInFile (String fileName){
        ArrayList <Book> books = readBooksInFile("book.DAT");
        ArrayList <Reader> readers = readReadersInFile("reader.DAT");

        openFileToRead(fileName);
        ArrayList<BRM> brms = new ArrayList<>();
        while (scanner.hasNextLine()){
            BRM brm = new BRM();
            String data = scanner.nextLine();
            brm = convertToBRM(data, books, readers);
            brms.add(brm);
        }
        closeFileAfterRead(fileName);
        return brms;
    }

    private BRM convertToBRM(String data, ArrayList<Book> books, ArrayList <Reader> readers){

        //public BRM(Book book, Reader reader, int borrowNumber, String status)
        //brm.getBook().getBookID() + "|" + brm.getReader().getReaderID() +
        //                "|" + brm.getBorrowNumber() + "|" + brm.getStatus()

        String datas[] = data.split("[|]");
        BRM brm = new BRM(getBook(books, Integer.parseInt(datas[0])), getReader(readers, Integer.parseInt(datas[1])),
                Integer.parseInt(datas[2]), datas[3]);
        return brm;
    }
    public void updateBRMFile(ArrayList <BRM> brms, String fileName){
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        openFileToWrite(fileName);
        for(BRM brm: brms){
            printWriter.println(brm.getBook().getBookID() + "|" + brm.getReader().getReaderID() +
                    "|" + brm.getBorrowNumber() + "|" + brm.getStatus());
        }
        closeFileAfterWrite(fileName);
    }

    private Book getBook(ArrayList <Book> books, int bID){
        for(Book book : books){
            if(book.getBookID() == bID)
                return book;
        }
        return null;
    }

    private Reader getReader(ArrayList <Reader> readers, int rID){
        for(Reader reader : readers){
            if(reader.getReaderID() == rID)
                return reader;
        }
        return null;
    }
}
