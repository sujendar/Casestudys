package com.digitalbooks.interfaces;

import java.util.List;

import com.digitalbooks.entity.BooksDetails;
import com.digitalbooks.entity.ResponseParms;
import com.digitalbooks.entity.SubscribeBook;

public interface IBooksSubscribeService {

	ResponseParms subscribeBook(SubscribeBook book);

	List<BooksDetails> getSubscribedBooks(Long valueOf);

	ResponseParms cancelSubscription(Long valueOf);

}
