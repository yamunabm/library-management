package com.hexad.library.managemnent.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hexad.library.management.controller.BookStorageController;
import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.service.BookStorageService;

@ExtendWith(MockitoExtension.class)
public class BookStorageControllerTest {

	@InjectMocks
	private BookStorageController bookStorageController;

	@Mock
	private BookStorageService bookStorageService;

	@Test
	public void getBooksCatalog_whenNoBooksPresent_ReturnEmptyList() throws BookNotFoundException {
		ResponseEntity<List<Book>> booksCatalog = bookStorageController.getBooksCatalog();
		assertTrue(booksCatalog.getBody().isEmpty());
	}

	@Test
	public void getBarrowedBooks_whenNoBooksPresent_returnEmptyList() throws BookNotFoundException {
		ResponseEntity<List<Book>> books = bookStorageController.getBarrowedBooks("123");
		assertTrue(books.getBody().isEmpty());
	}

	@Test
	@Disabled
	public void resetStorage() throws BookNotFoundException {

		ResponseEntity<?> addBookToStorage = bookStorageController.addBookToStorage(any());

		assertTrue(addBookToStorage.getStatusCode() == HttpStatus.OK);
		bookStorageController.resetStorage();

		ResponseEntity<List<Book>> booksCatalog = bookStorageController.getBooksCatalog();
		assertTrue(booksCatalog.getBody().isEmpty());
	}

	@Test
	@Disabled
	public void getStock_whenNoBooksPresent_thenReturnBookNotFoundException() throws BookNotFoundException {

		when(bookStorageService.getStock(anyString())).thenThrow(BookNotFoundException.class);

		assertThrows(BookNotFoundException.class, () -> {
			bookStorageController.getStock("123");
		});
	}

	@Test
	public void getStock_whenBooksPresent_thenreturnBookStock() throws BookNotFoundException {

		when(bookStorageService.getStock(anyString())).thenReturn(2);

		ResponseEntity<Integer> stock = bookStorageController.getStock("123");

		assertEquals(2, stock.getBody());
	}

}
