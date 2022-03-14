package com.hexad.library.management.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.model.Response;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

	List<Book> bookList;

	public BookServiceImpl() {
		this.bookList = new ArrayList<Book>();
	}

	@Override
	public Book getbook(String bookId) throws BookNotFoundException {
		Optional<Book> bookOptional = this.bookList.stream().filter(id -> bookId.equals(bookId)).findAny();

		if (!bookOptional.isPresent()) {
			log.error("Book {} Does not exists", bookId);
			throw new BookNotFoundException("Book Does not exists");
		}

		return bookOptional.get();
	}

	@Override
	public Response addbook(Book book) {

		Optional<Book> bookOptional = bookList.stream().filter(id -> id.getId().equals(book.getId())).findAny();
		Response response = new Response();
		response.setId(book.getId());
		if (bookOptional.isPresent()) {
			int indexOf = this.bookList.indexOf(bookOptional.get());
			bookList.set(indexOf, book);
			response.setMessage("Updated succesfully!");
		} else {
			this.bookList.add(book);
			response.setMessage("Saved succesfully!");
		}
		log.info("Book {} {}", response.getId(), response.getMessage());
		return response;
	}

}
