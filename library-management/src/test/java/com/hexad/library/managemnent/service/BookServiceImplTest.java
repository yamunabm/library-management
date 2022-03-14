package com.hexad.library.managemnent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.model.Response;
import com.hexad.library.management.service.BookServiceImpl;

@ExtendWith(MockitoExtension.class)

public class BookServiceImplTest {
	@InjectMocks
	private BookServiceImpl bookService;

	@BeforeEach
	public void before() {

	}

	@Test
	public void getBook_WhenNoBookPresent_shouldThrowBookNotFoundException() {
		assertThrows(BookNotFoundException.class, () -> {
			bookService.getbook("123");
		});
	}

	@Test
	public void addBook_WhenNoBooksPresent() throws BookNotFoundException {
		String bookId = "123";
		Book book = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();
		Response response = bookService.addbook(book);
		assertEquals(bookId, response.getId());
		assertNotNull(bookService.getbook(bookId));
	}
	
	@Test
	public void updateBook_WhenBookPresent() throws BookNotFoundException {
		String bookId = "123";
		Book book = Book.builder().id(bookId).isbn("ISBN123").title("System Design").build();
		bookService.addbook(book);
		assertEquals("ISBN123", bookService.getbook(bookId).getIsbn());
		book.setIsbn("ISBN321");
		bookService.addbook(book);
		assertEquals("ISBN321", bookService.getbook(bookId).getIsbn());
	}
}
