package com.digitalbooks.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BOOK_DETAILS")
public class BooksDetails {
	@Id
	@GeneratedValue
	private Long bookId;
	@Column(name="LOGO")
	private String logo;
	@Column(name="TITLE")
	private String title;
	@Column(name="CATEGORY")
	private String category;
	@Column(name="PRICE")
	private Double price;
	@Column(name="AUTHOR")
	private String author;
	@Column(name="PUBLISHER")
	private String publisher;
	@Column(name="PUBLISHEDDATE")
	private Date publisheddate;
	@Column(name="CONTENTS")
	private String contents;
	@Column(name="ACTIVE")
	private Integer active;
	@Column(name="AUTHORID")
	private Long authorId;
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="bookId", nullable=true)
	private SubscribeBook scBook;*/

public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	//	public SubscribeBook getScBook() {
//		return scBook;
//	}
//	public void setScBook(SubscribeBook scBook) {
//		this.scBook = scBook;
//	}
	public String getLogo() {
		return logo;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Date getPublisheddate() {
		return publisheddate;
	}
	public void setPublisheddate(Date publisheddate) {
		this.publisheddate = publisheddate;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}

}
