package com.hexad.library.managemnent.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
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
	public void getStock_whenNoBooksPresent_return_0() throws BookNotFoundException {

		ResponseEntity<Integer> stock = bookStorageController.getStock("123");
		assertTrue(stock.getBody() == 0);
	}

	@Test
	public void getBooksCatalog_whenNoBooksPResent_ReturnEmptyList() throws BookNotFoundException {
		ResponseEntity<List<Book>> booksCatalog = bookStorageController.getBooksCatalog();
		assertTrue(booksCatalog.getBody().isEmpty());
	}


	@Test
	public void getBarrowedBooks_whenNoBooksPresent_returnEmptyList() throws BookNotFoundException {
		ResponseEntity<List<Book>> books = bookStorageController.getBarrowedBooks("123");
		assertTrue(books.getBody().isEmpty());
	}

}
