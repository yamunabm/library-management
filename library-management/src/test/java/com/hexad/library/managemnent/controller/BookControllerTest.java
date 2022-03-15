package com.hexad.library.managemnent.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.hexad.library.management.controller.BookController;
import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

	@InjectMocks
	private BookController bookController;

	@Mock
	private BookService bookService;

	@Test
	public void getbook_noBookPresent_thenThrowBookNotFoundException() throws BookNotFoundException {

		when(bookService.getbook(anyString())).thenThrow(BookNotFoundException.class);

		assertThrows(BookNotFoundException.class, () -> {
			bookController.getbook("123");
		});
	}

	@Test
	public void getbook_BookPresent_thenReturnBook() throws BookNotFoundException {
		String bookId = "123";
		Book book = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();
		when(bookService.getbook(anyString())).thenReturn(book);

		ResponseEntity<Book> getbook = bookController.getbook(bookId);
		assertEquals(getbook.getBody(), book);
	}
}
