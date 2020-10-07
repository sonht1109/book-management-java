package main;

import controller.DataControl;
import controller.OtherControl;
import controller.SortBRMByName;
import model.BRM;
import model.Book;
import model.Reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        int choice;
        DataControl dataControl = new DataControl();
        OtherControl otherControl = new OtherControl();
        String bookFileName = "book.DAT";
        String readerFileName = "reader.DAT";
        String brmFileName = "brm.DAT";
        ArrayList<Book> books = null;
        ArrayList<Reader> readers = null;
        ArrayList<BRM> brms = null;
        Scanner scanner = new Scanner(System.in);
        do {
            showMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0:
                    System.out.println("Thank you !");
                    break;
                case 1:
                    //int bookID, String bookName, String author, String spec, int publishYear, int quantity

                    String specs[] = {"Science", "Art", "Economic", "IT"};

                    String name;
                    String author;
                    String spec;
                    int x, id;
                    int year, quantity;
                    books = dataControl.readBooksInFile(bookFileName);
                    id = (books == null) ? 1000 : 1000 + books.size();
                    System.out.println("Input bookName: ");
                    name = scanner.nextLine();
                    System.out.println("Input author: ");
                    author = scanner.nextLine();
                    System.out.println("Input specialization: ");
                    System.out.println("1. Science.");
                    System.out.println("2. Art.");
                    System.out.println("3. Economic.");
                    System.out.println("4. IT.");
                    do {
                        x = scanner.nextInt();
                        if (x < 1 || x > 4)
                            System.out.println("Error !!!");
                    }
                    while (x < 1 || x > 4);
                    spec = specs[x - 1];
                    System.out.println("Input publish year: ");
                    year = scanner.nextInt();
                    System.out.println("Input quantity: ");
                    quantity = scanner.nextInt();
                    Book book = new Book(id, name, author, spec, year, quantity);
                    dataControl.writeBooksToFile(book, bookFileName);
                    break;
                case 2:
                    books = dataControl.readBooksInFile(bookFileName);
                    showBooks(books);
                    break;
                case 3:
                    //int readerID, String readerName, String address, String phoneNum

                    String readerName, add, phone;
                    int readerID;
                    readers = dataControl.readReadersInFile(readerFileName);
                    readerID = (readers == null) ? 10000000 : 10000000 + readers.size();

                    System.out.println("Input reader name: ");
                    readerName = scanner.nextLine();
                    System.out.println("Input reader address: ");
                    add = scanner.nextLine();
                    System.out.println("Input reader phone number (10 digits): ");
                    do {
                        phone = scanner.nextLine();
                        if (!phone.matches("\\d{10}"))
                            System.out.println("Error !!!");
                    }
                    while (!phone.matches("\\d{10}"));
                    Reader reader = new Reader(readerID, readerName, add, phone);
                    dataControl.writeReadersToFile(reader, readerFileName);
                    break;
                case 4:
                    readers = dataControl.readReadersInFile(readerFileName);
                    showReaders(readers);
                    break;
                case 5:
                    // 1: read information of books and reader in file.
                    // 2: input a readerID, break if number of books of that reader is enough (15).
                    // 3: input a bookID, ask to choose another book if number of those books is enough (3).
                    // 4: input quantity of those book, input status of those book.
                    // 5: update BRM file.

                    // step 1:
                    books = dataControl.readBooksInFile(bookFileName);
                    readers = dataControl.readReadersInFile(readerFileName);
                    brms = dataControl.readBRMsInFile(brmFileName);

                    // step 2:
                    int rID;
                    boolean isBorrowable = false;
                    showReaders(readers);
                    int currentTotal = 0;
                    System.out.println("Input reader ID, 0 to exit: ");
                    do {
                        rID = scanner.nextInt();
                        if(rID == 0)
                            break;
                        if (checkReaderID(readers, rID)) {
                            currentTotal = checkBorrow(brms, rID);
                            System.out.println("==> THIS READER HAS BORROWED " + currentTotal + " BOOKS.");
                            if (currentTotal >= 15) {
                                System.out.println("Please choose another reader: ");
                            } else
                                break;
                        } else {
                            System.out.println("ID is not found !. Text again.");
                        }
                    }
                    while (true);

                    // step 3:
                    if(rID != 0){
                        int bID;
                        boolean isEnough = false;
                        showBooks(books);
                        System.out.println("Input book ID, 0 to exit: ");
                        do {
                            bID = scanner.nextInt();
                            if(bID == 0) break;
                            if (checkBookID(books, bID)) {
                                isEnough = checkEnough(brms, bID, rID);
                                if (isEnough) {
                                    System.out.println("PLease choose another book: ");
                                } else
                                    break;
                            } else
                                System.out.println("BookID is not found ! Text again.");
                        }
                        while (true);

                        // step 4:
                        if(bID != 0){
                            int totalOfABook = getTotalOfABook(brms, bID, rID);
                            System.out.println("Input number of those books (have borrowed " + totalOfABook + "): ");
                            do {
                                int tmp = scanner.nextInt();
                                if (tmp + totalOfABook > 3 || tmp + totalOfABook < 0) {
                                    System.out.println("Error !!!");
                                } else {
                                    totalOfABook += tmp;
                                    break;
                                }
                                ;
                            }
                            while (true);

                            scanner.nextLine();
                            System.out.println("Input status of books: ");
                            String status = scanner.nextLine();

                            // step 5:
                            Book currentBook = getBookInformation(books, bID);
                            Reader currentReader = getReaderInformation(readers, rID);
                            BRM newBRM = new BRM(currentBook, currentReader, totalOfABook, status);
                            otherControl.updateBRM(brms, newBRM);
                            dataControl.updateBRMFile(brms, brmFileName);
                        }
                    }
                    break;
                case 6:
                    brms = dataControl.readBRMsInFile(brmFileName);
                    showBRM(brms);
                    break;
                case 7:
                    brms = dataControl.readBRMsInFile(brmFileName);
                    Collections.sort(brms, new SortBRMByName());
                    showBRM(brms);
                    break;
                case 8:
                    brms = dataControl.readBRMsInFile(brmFileName);
                    System.out.println("Input keyword: ");
                    String key = scanner.nextLine();
                    ArrayList <BRM> list = otherControl.searchReaderByName(brms, key);
                    if(list.size() == 0){
                        System.out.println("Null !!!");
                    }
                    else
                        showBRM(list);
                    break;
            }
        }
        while (choice != 0);
    }

    private static void showBRM(ArrayList <BRM> brms) {
        System.out.println("________LIST OF BRMs IN FILE________");
        for(BRM brm : brms){
            System.out.println(brm);
        }
        System.out.println("_______________________________________");
    }

    private static boolean checkBookID(ArrayList<Book> books, int bID) {
        for (Book book : books) {
            if (book.getBookID() == bID)
                return true;
        }
        return false;
    }

    private static boolean checkReaderID(ArrayList<Reader> readers, int rID) {
        for (Reader reader : readers) {
            if (reader.getReaderID() == rID)
                return true;
        }
        return false;
    }

    private static void showMenu() {
        System.out.println("============ MENU ============");
        System.out.println("1. Add a book.");
        System.out.println("2. Show list of books.");
        System.out.println("3. Add a reader.");
        System.out.println("4. Show list of readers.");
        System.out.println("5. Book-reader Management. ");
        System.out.println("6. Show list of BRMs. ");
        System.out.println("7. Rearrange list of BRMs by name. ");
        System.out.println("8. Search a reader by name. ");
        System.out.println("0. Exit. ");
        System.out.println("You choose ?");
    }

    private static void showReaders(ArrayList<Reader> readers) {
        System.out.println("________LIST OF READERS IN FILE________");
        for (Reader reader : readers) {
            System.out.println(reader);
        }
        System.out.println("_______________________________________");
    }

    private static void showBooks(ArrayList<Book> books) {
        System.out.println("________LIST OF BOOKS IN FILE________");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println("_______________________________________");
    }

    private static int checkBorrow(ArrayList<BRM> brms, int readerID) {
        int count = 0;
        for (BRM brm : brms) {
            if (readerID == brm.getReader().getReaderID()) {
                count += brm.getBorrowNumber();
            }
        }
        return count;
    }

    private static boolean checkEnough(ArrayList<BRM> brms, int bID, int rID) {
        for (BRM brm : brms) {
            if (bID == brm.getBook().getBookID() && rID == brm.getReader().getReaderID()
                    && brm.getBorrowNumber() == 3) {
                return true;
            }
        }
        return false;
    }

    private static int getTotalOfABook(ArrayList<BRM> brms, int bID, int rID) {
        for (BRM brm : brms) {
            if (bID == brm.getBook().getBookID() && rID == brm.getReader().getReaderID()) {
                return brm.getBorrowNumber();
            }
        }
        return 0;
    }

    private static Book getBookInformation(ArrayList<Book> books, int bID) {
        for (Book book : books) {
            if (book.getBookID() == bID)
                return book;
        }
        return null;
    }

    private static Reader getReaderInformation(ArrayList<Reader> readers, int rID) {
        for (Reader reader : readers) {
            if (reader.getReaderID() == rID)
                return reader;
        }
        return null;
    }
}
