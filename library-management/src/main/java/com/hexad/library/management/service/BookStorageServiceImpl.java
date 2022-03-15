package com.hexad.library.management.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.exception.NotAllowedToBarrowException;
import com.hexad.library.management.exception.OutOfStockException;
import com.hexad.library.management.exception.UserExceededBookCreditLimitException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.model.BookRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
	public List<Book> getBarrowedBooks(String userId) throws BookNotFoundException {
		List<String> bookIds = barrowList.getOrDefault(userId, Collections.emptyList());
		List<Book> books = new ArrayList<Book>();
		for (String bookId : bookIds) {
			books.add(bookService.getbook(bookId));
		}

		return books;
	}

	@Override
	public void barrowBook(String userId, List<String> bookIds) throws UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, BookNotFoundException, OutOfStockException {

		for (String bookId : bookIds) {
			// add to barrow list
			addToBarrowList(bookId, userId);

			bookService.getbook(bookId);

			Integer stock = bookStorage.get(bookId);
			if (stock == null) {
				log.error("Book {} is out of stock in library", bookId);
				throw new OutOfStockException("Book is out of stock in library");
			}

			// update stock
			if (stock == 1)
				bookStorage.remove(bookId);
			else
				bookStorage.put(bookId, bookStorage.get(bookId) - 1);
		}
	}

	private void addToBarrowList(String bookId, String userId)
			throws NotAllowedToBarrowException, UserExceededBookCreditLimitException {
		List<String> list = barrowList.get(userId);

		if (list == null) {
			barrowList.put(userId, new ArrayList<String>());
		} else {

			if (list.contains(bookId)) {
				log.error("Cant barrow copy of same book!");
				throw new NotAllowedToBarrowException("Cant barrow copy of same book!");
			}

			if (list.size() == 2) {
				log.error("User {}  has exceeded book credit limit!", userId);
				throw new UserExceededBookCreditLimitException("User" + userId + " has exceeded book credit limit!");
			}
		}
		barrowList.get(userId).add(bookId);
	}

	@Override
	public void returnBook(String userId, String bookId) throws BookNotFoundException {

		// remove from barrow list
		List<String> userBarrowList = barrowList.get(userId);
		userBarrowList.remove(bookId);

		// restore stock

		List<BookRequest> books = new ArrayList<BookRequest>();
		BookRequest book = new BookRequest();
		book.setBookId(bookId);
		book.setQuantity(1);
		books.add(book);
		addBookToStorage(books);
	}

	@Override
	public void resetStorage() {
		bookStorage.clear();
		log.info("Cleared Library storage!");
	}

	@Override
	public void addBookToStorage(List<BookRequest> bookRequest) throws BookNotFoundException {

		for (BookRequest books : bookRequest) {
			bookService.getbook(books.getBookId());
			bookStorage.put(books.getBookId(), bookStorage.getOrDefault(books.getBookId(), 0) + books.getQuantity());
			log.info("Book {} of quantity {} is added to library storage", books.getBookId(), books.getQuantity());
		}
	}

	@Override
	public Integer getStock(String bookId) throws BookNotFoundException {

		Book book = bookService.getbook(bookId);

		if (!bookStorage.isEmpty())
			return bookStorage.get(book.getId());

		return 0;

	}
}
