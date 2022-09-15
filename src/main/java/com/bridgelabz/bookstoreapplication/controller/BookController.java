package com.bridgelabz.bookstoreapplication.controller;

import com.bridgelabz.bookstoreapplication.dto.BookDTO;
import com.bridgelabz.bookstoreapplication.dto.ResponseDTO;
import com.bridgelabz.bookstoreapplication.entity.BookData;
import com.bridgelabz.bookstoreapplication.repository.BookRepository;
import com.bridgelabz.bookstoreapplication.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService iBookService;

    @Autowired
    private BookRepository bookRepository;

    //localhost:8080/book/get
    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getAllBooks() {
        List<BookData> bookData = iBookService.getAllBooks();
        ResponseDTO responseDTO = new ResponseDTO("Get call Success", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/id
    @GetMapping("/id")
    public ResponseEntity<ResponseDTO> getBookById(@PathVariable int id) {
        Optional<BookData> bookData = iBookService.getBooksById(id);
        ResponseDTO responseDTO = new ResponseDTO("Get call Success for id successfully", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/name/{bookName}
    @GetMapping(value = "/name/{bookName}")
    public ResponseEntity<ResponseDTO> getBookByName(@PathVariable String bookName) {
        List<BookData> bookData = bookRepository.findBookByName(bookName);
        ResponseDTO responseDTO = new ResponseDTO("Get call Success For BookName Successfully", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/authorName/{authorName}
    @GetMapping("/authorName/{authorName}")
    public ResponseEntity<ResponseDTO> getBookByAuthorName(@PathVariable String authorName) {
        List<BookData> bookData = iBookService.getBookByAuthorName(authorName);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success For AuthorName Successfully", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/create
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> addBooks(@Valid @RequestBody BookDTO bookDTO) {
        String bookData = iBookService.insert(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Created Book Data Successfully", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/update/{id}
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateBooksById(@PathVariable int id, @Valid @RequestBody BookDTO bookDTO) {
        BookData bookData = iBookService.updateBooksById(id, bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated Book Data Successfully", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/update/{token}/{quantity}
    @PutMapping("/update/{token}/{quantity}")
    public ResponseEntity<ResponseDTO> updateBooksByQuantity(@PathVariable String token, @PathVariable int quantity) {
        BookData bookData = iBookService.updateBooksByQuantity(token, quantity);
        ResponseDTO responseDTO = new ResponseDTO("Updated Book Data Successfully", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/{token}
    @DeleteMapping("/{token}")
    public ResponseEntity<ResponseDTO> deleteBookData(@PathVariable int id) {
        iBookService.deletebookData(id);
        ResponseDTO responseDTO = new ResponseDTO("Deleted Successfully", id);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/ascSort
    @GetMapping("/ascSort")
    public ResponseEntity<ResponseDTO> sortBookDataAsc() {
        List<BookData> bookData = iBookService.sortBookDataAsc();
        ResponseDTO responseDTO = new ResponseDTO("Book Data In Ascending Order:", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    //localhost:8080/book/desSort
    @GetMapping("/desSort")
    public ResponseEntity<ResponseDTO> sortBookDataDesc() {
        List<BookData> bookData = iBookService.sortBookDataDesc();
        ResponseDTO responseDTO = new ResponseDTO("Book Data In Descending Order:", bookData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

}
