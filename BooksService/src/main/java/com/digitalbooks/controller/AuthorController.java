package com.digitalbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.entity.BooksDetails;
import com.digitalbooks.entity.ResponseParms;
import com.digitalbooks.entity.SubscribeBook;
import com.digitalbooks.interfaces.IBooksService;

@RestController
@RequestMapping("/app/v1/digitalbooks")
public class AuthorController {

	@Autowired
	private IBooksService booksService;
	
	@PostMapping(value="/author/books")
	public ResponseEntity<?> createBook(@RequestBody BooksDetails book) {
		ResponseParms response=booksService.createBook(book);
		return new ResponseEntity<ResponseParms>(response,HttpStatus.CREATED);
	}
	@PutMapping(value="/author/books/{booksid}")
	public ResponseEntity<?> upadteBook(@RequestBody BooksDetails book,@PathVariable Long booksid) {
		book.setBookId(booksid);
		ResponseParms response=booksService.upadteBook(book);
		return new ResponseEntity<ResponseParms>(response,HttpStatus.OK);
	}
	@PostMapping(value="/author/books/{authorid}/{bookid}")
	public ResponseEntity<?> blockUnblockBook(@PathVariable Long authorid,@PathVariable Long bookid,@RequestParam String block) {
		ResponseParms response=booksService.blockUnblockBook(authorid,bookid,block);
		return new ResponseEntity<ResponseParms>(response,HttpStatus.OK);
	}
}
