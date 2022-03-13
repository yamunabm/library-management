package com.hexad.library.management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.model.Response;

@Service
public class BookServiceImpl implements BookService {

	List<Book> bookList;

	public BookServiceImpl() {
		this.bookList = new ArrayList<Book>();
	}

	@Override
	public Book getbook(String bookId) throws BookNotFoundException {
		
		return null;
	}

	@Override
	public Response addbook(Book book) {

		Response response = new Response();
		
		return response;
	}

}
