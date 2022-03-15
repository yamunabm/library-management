package com.hexad.library.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.exception.NotAllowedToBarrowException;
import com.hexad.library.management.exception.OutOfStockException;
import com.hexad.library.management.exception.UserExceededBookCreditLimitException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.model.BookRequest;
import com.hexad.library.management.service.BookStorageService;

@RestController
@RequestMapping(path = "/library/v1/bookstorage")
public class BookStorageController {

	@Autowired
	private BookStorageService bookStorageService;

	@GetMapping(value = "/catalog")
	public ResponseEntity<List<Book>> getBooksCatalog() throws BookNotFoundException {
		List<Book> booksCatalog = bookStorageService.getBooksCatalog();
		return ResponseEntity.ok(booksCatalog);
	}

	@PostMapping
	public ResponseEntity<?> addBookToStorage(@RequestBody List<BookRequest> bookRequest) throws BookNotFoundException {
		bookStorageService.addBookToStorage(bookRequest);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "barrow")
	public ResponseEntity<?> barrowBook(@RequestParam String userId, @RequestParam List<String> bookIds)
			throws UserExceededBookCreditLimitException, NotAllowedToBarrowException, BookNotFoundException,
			OutOfStockException {
		bookStorageService.barrowBook(userId, bookIds);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/{userId}")
	public ResponseEntity<List<Book>> getBarrowedBooks(@PathVariable("userId") String userId)
			throws BookNotFoundException {
		List<Book> books = bookStorageService.getBarrowedBooks(userId);
		return ResponseEntity.ok(books);
	}

	@GetMapping(value = "/stock/{bookId}")
	public ResponseEntity<Integer> getStock(@PathVariable("bookId") String bookId) throws BookNotFoundException {
		Integer stock = bookStorageService.getStock(bookId);
		return ResponseEntity.ok(stock);
	}

	@PostMapping(value = "return")
	public ResponseEntity<?> returnBook(String userId, String bookId) throws BookNotFoundException {
		bookStorageService.returnBook(userId, bookId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping
	public ResponseEntity<?> resetStorage() {
		bookStorageService.resetStorage();
		return ResponseEntity.ok().build();
	}
}
