package com.digitalbooks.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="SubscribeBook")
public class SubscribeBook {
	@Id
	@GeneratedValue
	private Long subscriptionId;
	
	private Long bookId;
	
	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	private Long userId;
	
	private Date subscriptionDate;
//	@OneToMany(mappedBy = "bookId")
//	private List<BooksDetails> booksDetails;
//	
//	public List<BooksDetails> getBooksDetails() {
//		return booksDetails;
//	}
//
//	public void setBooksDetails(List<BooksDetails> booksDetails) {
//		this.booksDetails = booksDetails;
//	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	


}
