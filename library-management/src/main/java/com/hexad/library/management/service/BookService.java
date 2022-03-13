package com.hexad.library.management.service;

import com.hexad.library.management.exception.BookNotFoundException;
import com.hexad.library.management.model.Book;
import com.hexad.library.management.model.Response;

public interface BookService {

	Book getbook(String bookId) throws BookNotFoundException;

	Response addbook(Book book);

}
