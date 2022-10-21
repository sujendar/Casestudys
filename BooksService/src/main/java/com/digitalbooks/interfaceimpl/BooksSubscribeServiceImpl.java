package com.digitalbooks.interfaceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.dao.BooksServiceDao;
import com.digitalbooks.dao.BooksSubscribeDao;
import com.digitalbooks.entity.BooksDetails;
import com.digitalbooks.entity.ResponseParms;
import com.digitalbooks.entity.SubscribeBook;
import com.digitalbooks.interfaces.IBooksConstants;
import com.digitalbooks.interfaces.IBooksSubscribeService;
@Service
public class BooksSubscribeServiceImpl implements IBooksSubscribeService{
	@Autowired
	private BooksServiceDao booksservicedao;
	@Autowired
	private BooksSubscribeDao subscribeDao;
	@Override
	public ResponseParms subscribeBook(SubscribeBook book) {
		book.setSubscriptionDate(new Date());
		subscribeDao.save(book);
		ResponseParms params=new ResponseParms();
		params.setErrorcode(IBooksConstants.success_code.toString());
		params.setErrormessage(IBooksConstants.success_msg.toString());
		return params;
	}
	@Override
	public List<BooksDetails> getSubscribedBooks(Long userId) {
		List<BooksDetails> list=booksservicedao.getSubscribedBooks(userId);
		return list;
	}
	@Override
	public ResponseParms cancelSubscription(Long subscribeId) {
		ResponseParms response=new ResponseParms();
		SubscribeBook subscribeDetails=subscribeDao.findBySubscriptionId(subscribeId);
		long difference_In_Time
        = new Date().getTime()-subscribeDetails.getSubscriptionDate().getTime();
		 long difference_In_hours
         = (difference_In_Time
        	/	 (1000 * 60 * 60));
		 System.out.println("difference_In_hours :"+difference_In_hours);
		 if(difference_In_hours < 24) {
			 subscribeDao.delete(subscribeDetails);
			 response.setErrorcode(IBooksConstants.success_code.toString());
			 response.setErrormessage(IBooksConstants.success_msg.toString());
		 }else {
			 response.setErrorcode(IBooksConstants.success_code.toString());
			 response.setErrormessage("Your subscription date crosses 24 hrs.now you cant unsubscribe.");
		 }
		return response;
	}
}
