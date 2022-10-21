package com.digitalbooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.entity.BooksDetails;
import com.digitalbooks.entity.ResponseParms;
import com.digitalbooks.entity.SubscribeBook;
import com.digitalbooks.interfaces.IBooksService;
import com.digitalbooks.interfaces.IBooksSubscribeService;



@RestController
@RequestMapping("/app/v1/digitalbooks")
public class BooksController {
	@Autowired
	private IBooksService booksService;
	@Autowired
	private IBooksSubscribeService booksSubscribe;
	@GetMapping(value="/hi")
	public String sayHi() {
		return "Hi";
	}
	
	@GetMapping(value="/search")
	public ResponseEntity<List<BooksDetails>> search(@RequestParam String category,@RequestParam String title,
			@RequestParam String author,@RequestParam String price,
			@RequestParam String publisher) {
		List<BooksDetails> list=booksService.search(category,title,author,price,publisher);
		System.out.println(list.get(0).getBookId());
		return new ResponseEntity<List<BooksDetails>>(list,HttpStatus.OK);
	}
	
	@PostMapping(value="/subscribe")
	public ResponseEntity<?> subscribeBook(@RequestBody SubscribeBook book) {
		ResponseParms response=booksSubscribe.subscribeBook(book);
		return new ResponseEntity<ResponseParms>(response,HttpStatus.OK);
	}
	
	@GetMapping(value="/books/{userId}")
	public ResponseEntity<List<BooksDetails>> getSubscribedBooks(@PathVariable String userId) {
		List<BooksDetails> list=booksSubscribe.getSubscribedBooks(Long.valueOf(userId));
		return new ResponseEntity<List<BooksDetails>>(list,HttpStatus.OK);
}
}
