package com.hexad.library.management.service;

import java.util.List;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.exception.ISBNDoesNotExistsException;
import com.hexad.library.management.exception.NotAllowedToBarrowException;
import com.hexad.library.management.exception.OutOfStockException;
import com.hexad.library.management.exception.UserExceededBookCreditLimitException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.model.BookRequest;

public interface BookStorageService {

	List<Book> getBooksCatalog() throws BookNotFoundException;

	List<Book> getBarrowedBooks(String userId) throws BookNotFoundException;

	Integer getStock(String bookId) throws BookNotFoundException;

	void barrowBook(String userId, List<String> bookIds)
			throws  ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, BookNotFoundException, OutOfStockException;

	void returnBook(String userId, String bookId) throws  BookNotFoundException;

	void resetStorage();

	void addBookToStorage(List<BookRequest> bookRequest) throws BookNotFoundException;

}
