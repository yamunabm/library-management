package com.hexad.library.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.exception.ISBNDoesNotExistsException;
import com.hexad.library.management.exception.NotAllowedToBarrowException;
import com.hexad.library.management.exception.OutOfStockException;
import com.hexad.library.management.exception.UserExceededBookCreditLimitException;
import com.hexad.library.management.exception.UserNotFoundException;
import com.hexad.library.management.model.Book;

@Service
public class BookStorageServiceImpl implements BookStorageService {

	@Autowired
	private BookService bookService;

	private Map<String, Integer> bookStorage;

	private Map<String, List<String>> barrowList;

	public BookStorageServiceImpl() {
		this.bookStorage = new HashMap<String, Integer>(16);
		this.barrowList = new HashMap<String, List<String>>();
	}

	@Override
	public List<Book> getBooksCatalog() throws BookNotFoundException {
		Set<String> bookIds = bookStorage.keySet();
		List<Book> books = new ArrayList<Book>();
		for (String bookId : bookIds) {
			books.add(bookService.getbook(bookId));
		}
		return books;
	}

	@Override
	public void addBookToStorage(String bookId, int quantity) throws BookNotFoundException {
		bookService.getbook(bookId);
		bookStorage.put(bookId, bookStorage.getOrDefault(bookId, 0) + quantity);
	}

	@Override
	public List<Book> getBarrowedBooks(String userId) throws BookNotFoundException {
		List<Book> books = new ArrayList<Book>();

		return books;
	}

	@Override
	public void barrowBook(String userId, String bookId)
			throws UserNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, BookNotFoundException, OutOfStockException {

	}

	@Override
	public void returnBook(String userId, String bookId) throws UserNotFoundException, BookNotFoundException {

	}

	@Override
	public void resetStorage() {
		bookStorage.clear();
	}

	@Override
	public Integer getStock(String bookId) throws BookNotFoundException {
		Book book = bookService.getbook(bookId);
		if (!bookStorage.isEmpty())
			return bookStorage.get(book.getId());
		return 0;
	}

}
