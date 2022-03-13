package com.hexad.library.managemnent.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexad.library.management.exception.BookNotFoundException;
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

	// TODO : Test case List

	// getBooksWhenNoBooksPresent_shouldReturnEmptyList
	// getBooksWhenBooksPresent_shouldReturnBooksList

	// addBooksWhenNoBooksPresent_bookShouldBeAddedToStorage
	// addCopyOfBooksWhenNoCopiesPresent_stockOfSameBookShouldReturnTwo

	// barrowBook_thenBookAddedToBarrowList_AndBookIsRemovedFromLibrary
	// barrowBook_UserExceededBookCreditLimit_thenThrowException

	// barrowBook_moreCopyOfBookPresent_thenUserTriesToBuyMoreThanOneCopy_andThrowException
	// barrowBook_NoBooksInStock_thenThrowOtOfStockException

	// barrowBook_moreCopyOfBookPresent_thenBookAddedToBarrowList_andAtleastOneCopyOfBookPresentInLibrary
	// barrowBook_oneCopyOfBookPresent_thenBookAddedToBarrowList_andBookIsRemovedFromLibrary

	// returnOneBook_twoBooksInBarrowList_thenOneBookRemovedFromBarrowList_AndLibraryStockCountUpdated

	// returnTwoBooks_twoBooksInBarrowList_thenBarrowListBecomesEmpty_AndLibraryStockCountUpdated

}
