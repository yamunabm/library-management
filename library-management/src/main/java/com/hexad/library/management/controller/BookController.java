package com.hexad.library.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.model.Response;
import com.hexad.library.management.service.BookService;

@RestController
@RequestMapping(path = "/library/v1/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	public ResponseEntity<Book> getbook(String bookId) throws BookNotFoundException {
		Book book = bookService.getbook(bookId);
		return ResponseEntity.ok(book);
	}

	@PostMapping
	public ResponseEntity<Response> addbook(Book book) {
		Response response = bookService.addbook(book);
		return ResponseEntity.ok(response);
	}
}
