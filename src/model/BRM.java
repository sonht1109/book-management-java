package model;

import java.io.Serializable;

public class BRM implements Serializable {
    private Book book;
    private Reader reader;
    private int borrowNumber;
    private String status;

    public BRM() {
    }

    public BRM(Book book, Reader reader, int borrowNumber, String status) {
        this.book = book;
        this.reader = reader;
        this.borrowNumber = borrowNumber;
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public int getBorrowNumber() {
        return borrowNumber;
    }

    public void setBorrowNumber(int borrowNumber) {
        this.borrowNumber = borrowNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BRM{" +
                "readerID=" + reader.getReaderID() +
                ", readerName= '" + reader.getReaderName() + '\'' +
                ", bookID=" + book.getBookID() +
                ", bookName= '" + book.getBookName() + '\'' +
                ", borrowNumber=" + borrowNumber +
                ", status='" + status + '\'' +
                '}';
    }
}
