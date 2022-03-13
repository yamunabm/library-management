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
import com.hexad.library.management.exception.UserNotFoundException;
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
