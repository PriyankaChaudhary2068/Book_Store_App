package com.bridgelabz.bookstoreapplication.service;

import com.bridgelabz.bookstoreapplication.util.TokenUtil;
import com.bridgelabz.bookstoreapplication.dto.BookDTO;
import com.bridgelabz.bookstoreapplication.entity.BookData;
import com.bridgelabz.bookstoreapplication.exception.BookStoreException;
import com.bridgelabz.bookstoreapplication.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TokenUtil util;

    @Override
    public String insert(BookDTO bookDTO) {
        BookData bookData = new BookData(bookDTO);
        bookRepository.save(bookData);
        String token = util.createToken(bookData.getBookId());
        return token;
    }

    @Override
    public List<BookData> getAllBooks() {
        List<BookData> listOfBooks = bookRepository.findAll();
        return listOfBooks;
    }

    @Override
    public Optional<BookData> getBooksById(int id) {
        Optional<BookData> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            return bookData;
        } else {
            throw new BookStoreException("Exception With id" + id + "Does Not Exist!!");
        }
    }


    @Override
    public List<BookData> getBookByName(String bookName) {
        List<BookData> findBook = bookRepository.findBookByName(bookName);
        if (findBook.isEmpty()) {
            throw new BookStoreException(" Details For Provided Book Is Not Found");
        }
        return findBook;
    }

    @Override
    public List<BookData> getBookByAuthorName(String authorName) {
        List<BookData> findBook = bookRepository.findBookByAuthorName(authorName);
        if (findBook.isEmpty()) {
            throw new BookStoreException(" Book Author Name Is Not Found");
        }
        return findBook;
    }


    @Override
    public BookData updateBooksById(int id, BookDTO bookDTO) {

        Optional<BookData> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            BookData updateData = new BookData(id, bookDTO);
            bookRepository.save(updateData);
            return updateData;
        } else {
            throw new BookStoreException("BookData Record Does Not Found");
        }
    }

    @Override
    public BookData updateBooksByQuantity(String token, int quantity) {
        int id = util.decodeToken(token);
        Optional<BookData> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            BookData bookData1 = new BookData();
            bookData1.setQuantity(quantity);
            bookRepository.save(bookData1);
            return bookData1;
        } else {
            throw new BookStoreException("BookData Record Does Not Found");
        }
    }

    @Override
    public List<BookData> sortBookDataAsc() {
        return bookRepository.sortBookDataAsc();
    }

    @Override
    public List<BookData> sortBookDataDesc() {
        return bookRepository.sortBookDataDesc();
    }

    @Override
    public void deletebookData(int id) {
        Optional<BookData> bookData = bookRepository.findById(id);
        if (bookData.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookStoreException("Book Record Does Not Found");
        }
    }
}
