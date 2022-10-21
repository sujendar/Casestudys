package com.digitalbooks.dao;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.digitalbooks.entity.BooksDetails;
import com.digitalbooks.entity.SubscribeBook;




public interface BooksServiceDao extends JpaRepository<BooksDetails, Long> {

	public List<BooksDetails> findByCategory(String category);
    @Query("SELECT b FROM BooksDetails b JOIN SubscribeBook s on b.bookId=s.bookId where s.userId=?1")
	public List<BooksDetails> getSubscribedBooks(Long userId);
    @Query("SELECT b FROM BooksDetails b JOIN SubscribeBook s on b.bookId=s.bookId where s.subscriptionId=?1")
	public BooksDetails findBySubscriptionId(Long subscriptionId);
	public BooksDetails findByBookId(Long bookId);
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("UPDATE BooksDetails b SET b.active=:active WHERE b.bookId=:bookId")
	public void upadteBlockBook(@Param("bookId") Long bookId,@Param("active") Integer blockBook);


   
	
}
