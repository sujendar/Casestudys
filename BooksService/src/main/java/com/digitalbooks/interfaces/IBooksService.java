package com.digitalbooks.interfaces;

import java.util.List;

import com.digitalbooks.entity.BooksDetails;
import com.digitalbooks.entity.ResponseParms;

public interface IBooksService {
	List<BooksDetails> search(String category, String title, String author, String price, String publisher);

	BooksDetails getBookDetails(Long bookId);

	String getBookContent(Long bookId);

	ResponseParms createBook(BooksDetails book);

	ResponseParms upadteBook(BooksDetails book);

	ResponseParms blockUnblockBook(Long authorid, Long bookid, String block);
}
