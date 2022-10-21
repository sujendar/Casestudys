package com.digitalbooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.entity.BooksDetails;
import com.digitalbooks.entity.ResponseParms;
import com.digitalbooks.interfaces.IBooksService;
import com.digitalbooks.interfaces.IBooksSubscribeService;
@RestController
@RequestMapping("/app/v1/digitalbooks/readers")
public class ReaderController {
	@Autowired
	private IBooksService booksService;
	@Autowired
	private IBooksSubscribeService booksSubscribe;
	
	@GetMapping(value="/books/{userId}")
	public ResponseEntity<List<BooksDetails>> getSubscribedBooks(@PathVariable String userId) {
		List<BooksDetails> list=booksSubscribe.getSubscribedBooks(Long.valueOf(userId));
		return new ResponseEntity<List<BooksDetails>>(list,HttpStatus.OK);
}
	@GetMapping(value="/books/{userId}/{subscribeId}")
	public ResponseEntity<BooksDetails> getBookDetails(@PathVariable String userId,@PathVariable String subscribeId) {
		BooksDetails bookDetails=booksService.getBookDetails(Long.valueOf(subscribeId));
		return new ResponseEntity<BooksDetails>(bookDetails,HttpStatus.OK);
}
	@GetMapping(value="/books/read/{subscribeId}")
	public ResponseEntity<String> getContent(@PathVariable String subscribeId) {
		String content=booksService.getBookContent(Long.valueOf(subscribeId));
		return new ResponseEntity<String>(content,HttpStatus.OK);
}
	@PostMapping(value="/books/cancelsubscription/{subscribeId}")
	public ResponseEntity<ResponseParms> cancelSubscribe(@PathVariable String subscribeId) {
		ResponseParms responseParams=booksSubscribe.cancelSubscription(Long.valueOf(subscribeId));
		return new ResponseEntity<ResponseParms>(responseParams,HttpStatus.OK);
}
}
