package com.digitalbooks.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.digitalbooks.entity.SubscribeBook;

public interface BooksSubscribeDao extends JpaRepository<SubscribeBook, Long>{

	SubscribeBook findBySubscriptionId(Long subscribeId);

}
