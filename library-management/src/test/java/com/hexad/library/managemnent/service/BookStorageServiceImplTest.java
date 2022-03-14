package com.hexad.library.managemnent.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.exception.ISBNDoesNotExistsException;
import com.hexad.library.management.exception.NotAllowedToBarrowException;
import com.hexad.library.management.exception.OutOfStockException;
import com.hexad.library.management.exception.UserExceededBookCreditLimitException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.service.BookService;
import com.hexad.library.management.service.BookStorageServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BookStorageServiceImplTest {

	@InjectMocks
	private BookStorageServiceImpl libraryServiceImpl;

	@Mock
	private BookService bookService;

	@BeforeEach
	public void before() {

	}

	@Test
	public void getBooksWhenNoBooksPresent_shouldReturnEmptyList() throws BookNotFoundException {
		List<Book> books = libraryServiceImpl.getBooksCatalog();
		assertTrue(books.isEmpty());
	}

	@Test
	public void addBooksWhenNoBooksPresent_bookShouldBeAddedToStorage() throws BookNotFoundException {
		String bookId_1 = "123";
		String bookId_2 = "456";
		Book book_1 = Book.builder().id(bookId_1).isbn("ISBN123").title("System Design").build();
		Book book_2 = Book.builder().id(bookId_2).isbn("ISBN123").title("System Design").build();

		when(bookService.getbook(bookId_1)).thenReturn(book_1);
		when(bookService.getbook(bookId_2)).thenReturn(book_2);

		libraryServiceImpl.addBookToStorage(bookId_1, 1);
		libraryServiceImpl.addBookToStorage(bookId_2, 1);

		assertTrue(libraryServiceImpl.getBooksCatalog().size() == 2);
	}

	@Test
	public void getBooksWhenBooksPresent_shouldReturnBooksList() throws BookNotFoundException {
		String bookId_1 = "123";
		String bookId_2 = "456";
		Book book_1 = Book.builder().id(bookId_1).isbn("ISBN123").title("System Design").build();
		Book book_2 = Book.builder().id(bookId_2).isbn("ISBN123").title("System Design").build();

		when(bookService.getbook(bookId_1)).thenReturn(book_1);
		when(bookService.getbook(bookId_2)).thenReturn(book_2);

		libraryServiceImpl.addBookToStorage(bookId_1, 1);
		libraryServiceImpl.addBookToStorage(bookId_2, 1);

		assertTrue(libraryServiceImpl.getBooksCatalog().size() == 2);
	}

	@Test
	public void addCopyOfBooksWhenNoCopiesPresent_stockOfSameBookShouldReturnTwo() throws BookNotFoundException {
		String bookId = "123";

		Book book = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();

		libraryServiceImpl.addBookToStorage(bookId, 2);

		when(bookService.getbook(bookId)).thenReturn(book);

		assertTrue(libraryServiceImpl.getStock(bookId) == 2);
	}

	@Test
	public void barrowBook_thenBookAddedToBarrowList_AndBookIsRemovedFromLibrary()
			throws BookNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, OutOfStockException {
		String bookId = "123";
		String userId = "user1";

		Book book = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();

		when(bookService.getbook(bookId)).thenReturn(book);
		libraryServiceImpl.addBookToStorage(bookId, 1);

		assertTrue(libraryServiceImpl.getStock(bookId) == 1);

		libraryServiceImpl.barrowBook(userId, bookId);

		assertTrue(libraryServiceImpl.getStock(bookId) == 0);
	}

	@Test
	public void barrowBook_moreCopyOfBookPresent_thenBookAddedToBarrowList_andAtleastOneCopyOfBookPresentInLibrary()
			throws BookNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, OutOfStockException {
		String bookId = "123";
		String userId = "user1";

		Book book = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();

		when(bookService.getbook(bookId)).thenReturn(book);
		libraryServiceImpl.addBookToStorage(bookId, 2);

		assertTrue(libraryServiceImpl.getStock(bookId) == 2);

		libraryServiceImpl.barrowBook(userId, bookId);

		assertTrue(libraryServiceImpl.getStock(bookId) == 1);
	}

	@Test
	public void barrowBook_oneCopyOfBookPresent_thenBookAddedToBarrowList_andBookIsRemovedFromLibrary()
			throws BookNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, OutOfStockException {
		String bookId = "123";
		String userId = "user1";

		Book book = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();

		when(bookService.getbook(bookId)).thenReturn(book);
		libraryServiceImpl.addBookToStorage(bookId, 1);

		assertTrue(libraryServiceImpl.getStock(bookId) == 1);

		libraryServiceImpl.barrowBook(userId, bookId);

		assertTrue(libraryServiceImpl.getStock(bookId) == 0);
	}

	@Test
	public void barrowBook_UserExceededBookCreditLimit_thenThrowException()
			throws BookNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, OutOfStockException {
		String bookId_1 = "123";
		String bookId_2 = "456";
		String bookId_3 = "789";
		String userId = "user1";

		Book book1 = Book.builder().id(bookId_1).isbn("ISBN123").title("System Design").build();
		Book book2 = Book.builder().id(bookId_2).isbn("ISBN456").title("Architecture").build();

		when(bookService.getbook(bookId_1)).thenReturn(book1);
		when(bookService.getbook(bookId_2)).thenReturn(book2);

		libraryServiceImpl.addBookToStorage(bookId_1, 1);
		libraryServiceImpl.addBookToStorage(bookId_2, 1);
		libraryServiceImpl.addBookToStorage(bookId_3, 1);

		libraryServiceImpl.barrowBook(userId, bookId_1);
		libraryServiceImpl.barrowBook(userId, bookId_2);

		assertThrows(UserExceededBookCreditLimitException.class, () -> {
			libraryServiceImpl.barrowBook(userId, bookId_3);
		});

	}

	@Test
	public void barrowBook_UserBuyingSamrCopyTwice_thenThrowException()
			throws BookNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, OutOfStockException {
		String bookId = "123";
		String userId_1 = "user1";

		Book book1 = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();
		when(bookService.getbook(bookId)).thenReturn(book1);

		libraryServiceImpl.addBookToStorage(bookId, 1);
		libraryServiceImpl.barrowBook(userId_1, bookId);

		assertThrows(NotAllowedToBarrowException.class, () -> {
			libraryServiceImpl.barrowBook(userId_1, bookId);
		});

	}

	@Test
	public void barrowBook_NoBooksInStock_thenThrowOtOfStockException()
			throws BookNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, OutOfStockException {
		String bookId = "123";
		String userId_1 = "user1";
		String userId_2 = "user2";

		Book book1 = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();
		when(bookService.getbook(bookId)).thenReturn(book1);

		libraryServiceImpl.addBookToStorage(bookId, 1);
		libraryServiceImpl.barrowBook(userId_1, bookId);

		assertThrows(OutOfStockException.class, () -> {
			libraryServiceImpl.barrowBook(userId_2, bookId);
		});

	}

	@Test
	public void returnOneBook_twoBooksInBarrowList_thenOneBookRemovedFromBarrowList_AndLibraryStockCountUpdated()
			throws BookNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, OutOfStockException {
		String bookId_1 = "123";
		String bookId_2 = "456";
		String userId = "user1";

		Book book1 = Book.builder().id(bookId_1).isbn("ISBN123").title("System Design").build();
		Book book2 = Book.builder().id(bookId_2).isbn("ISBN456").title("Architecture").build();

		when(bookService.getbook(bookId_1)).thenReturn(book1);
		when(bookService.getbook(bookId_2)).thenReturn(book2);

		libraryServiceImpl.addBookToStorage(bookId_1, 1);
		libraryServiceImpl.addBookToStorage(bookId_2, 1);

		assertTrue(libraryServiceImpl.getBooksCatalog().size() == 2);
		assertTrue(libraryServiceImpl.getBarrowedBooks(userId).size() == 0);

		libraryServiceImpl.barrowBook(userId, bookId_1);
		libraryServiceImpl.barrowBook(userId, bookId_2);

		assertTrue(libraryServiceImpl.getBooksCatalog().size() == 0);
		assertTrue(libraryServiceImpl.getBarrowedBooks(userId).size() == 2);

		libraryServiceImpl.returnBook(userId, bookId_1);

		assertTrue(libraryServiceImpl.getBooksCatalog().size() == 1);
		assertTrue(libraryServiceImpl.getBarrowedBooks(userId).size() == 1);
	}

	@Test
	public void returnTwoBooks_twoBooksInBarrowList_thenBarrowListBecomesEmpty_AndLibraryStockCountUpdated()
			throws BookNotFoundException, ISBNDoesNotExistsException, UserExceededBookCreditLimitException,
			NotAllowedToBarrowException, OutOfStockException {
		String bookId_1 = "123";
		String bookId_2 = "456";
		String userId = "user1";

		Book book1 = Book.builder().id(bookId_1).isbn("ISBN123").title("System Design").build();
		Book book2 = Book.builder().id(bookId_2).isbn("ISBN456").title("Architecture").build();

		when(bookService.getbook(bookId_1)).thenReturn(book1);
		when(bookService.getbook(bookId_2)).thenReturn(book2);

		libraryServiceImpl.addBookToStorage(bookId_1, 1);
		libraryServiceImpl.addBookToStorage(bookId_2, 1);

		assertTrue(libraryServiceImpl.getBooksCatalog().size() == 2);
		assertTrue(libraryServiceImpl.getBarrowedBooks(userId).size() == 0);

		libraryServiceImpl.barrowBook(userId, bookId_1);
		libraryServiceImpl.barrowBook(userId, bookId_2);

		assertTrue(libraryServiceImpl.getBooksCatalog().size() == 0);
		assertTrue(libraryServiceImpl.getBarrowedBooks(userId).size() == 2);

		libraryServiceImpl.returnBook(userId, bookId_1);
		libraryServiceImpl.returnBook(userId, bookId_2);

		assertTrue(libraryServiceImpl.getBooksCatalog().size() == 2);
		assertTrue(libraryServiceImpl.getBarrowedBooks(userId).size() == 0);
	}

	// TODO : Test case List

	// getBooksWhenNoBooksPresent_shouldReturnEmptyList - done
	// getBooksWhenBooksPresent_shouldReturnBooksList - done

	// addBooksWhenNoBooksPresent_bookShouldBeAddedToStorage - done
	// addCopyOfBooksWhenNoCopiesPresent_stockOfSameBookShouldReturnTwo - done

	// barrowBook_thenBookAddedToBarrowList_AndBookIsRemovedFromLibrary -done
	// barrowBook_UserExceededBookCreditLimit_thenThrowException - done

	// barrowBook_moreCopyOfBookPresent_thenUserTriesToBuyMoreThanOneCopy_andThrowException
	// - done
	// barrowBook_NoBooksInStock_thenThrowOtOfStockException - done

	// barrowBook_moreCopyOfBookPresent_thenBookAddedToBarrowList_andAtleastOneCopyOfBookPresentInLibrary
	// - done
	// barrowBook_oneCopyOfBookPresent_thenBookAddedToBarrowList_andBookIsRemovedFromLibrary
	// - done

	// returnOneBook_twoBooksInBarrowList_thenOneBookRemovedFromBarrowList_AndLibraryStockCountUpdated

	// returnTwoBooks_twoBooksInBarrowList_thenBarrowListBecomesEmpty_AndLibraryStockCountUpdated

}
