package com.security.api.controller;

import com.security.api.model.Book;
import com.security.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {
  
  @Autowired
  private BookRepository bookRepository;
  
  
  @PostMapping("/books")
  @ResponseStatus(HttpStatus.CREATED)
  public Book newBook(@RequestBody Book book) {
	return bookRepository.save(book);
	
  }
  
  @GetMapping("/books")
  @ResponseStatus(HttpStatus.OK)
  public Page<Book> getBookList(Pageable pageable) {
	return bookRepository.findAll(pageable);
  }
  
  @GetMapping("/books/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Book getBookByID(@PathVariable(value = "id") long id) throws Exception {
	return bookRepository.findById(id).orElseThrow(() -> new Exception("Book By the ID not found " + id));
  }
  
  @PutMapping("/books/{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Book updateBookDetails(@RequestBody Book book, @PathVariable(value = "id") long id) throws Exception {
	
	return bookRepository.findById(id)
			.map(update -> {
			  update.setName(book.getName());
			  update.setAuthor(book.getAuthor());
			  update.setPrice(book.getPrice());
			  return bookRepository.save(book);
			}).orElseThrow(() -> new Exception("Book not found by id"));
  }
  
  
}
