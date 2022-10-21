package com.digitalbooks.interfaceimpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.digitalbooks.dao.BooksServiceDao;
import com.digitalbooks.dao.BooksSubscribeDao;
import com.digitalbooks.entity.BooksDetails;
import com.digitalbooks.entity.ResponseParms;
import com.digitalbooks.entity.SubscribeBook;
import com.digitalbooks.interfaces.IBooksConstants;
import com.digitalbooks.interfaces.IBooksService;
@Service
public class BooksServiceImpl implements IBooksService {
	@Autowired
	private BooksServiceDao booksservicedao;
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
	private BooksSubscribeDao subscribeDao;
	@Autowired
    private JdbcTemplate jdbctemplate;
	@Override
	public List<BooksDetails> search(String category, String title, String author, String price, String publisher) {
		List<BooksDetails> list=null;
		System.out.println("category:"+category+" title:"+title+"author:"+author+""+"price: "+price+"publisher:"+publisher);
		/*
		 * if(category!=null) { list=booksservicedao.findByCategory(category); }
		 */
		
		 StringBuilder queryBuilder=new StringBuilder("SELECT active,author,category,contents,logo,price,publisheddate,publisher,title,book_id FROM BOOK_DETAILS");
		 if(category!=null || title!=null || author!=null || price!=null ||
		  publisher!=null ) { 
		 queryBuilder.append(" WHERE 1=1 ");
		 if(category!=null && !category.isEmpty()) {
			 queryBuilder.append("AND lower(category) LIKE lower('%"+ category+"%')");
		 }
		 if(title!=null && !title.isEmpty()) {
			 queryBuilder.append(" AND lower(title) LIKE lower('%"+ title+"%')");
		 }
		 if(author!=null && !author.isEmpty() ) {
			 queryBuilder.append(" AND lower(author) LIKE lower('%"+ author+"%')");
		 }
		 if(publisher!=null && !publisher.isEmpty()) {
			 queryBuilder.append(" AND lower(publisher) LIKE lower('%"+ publisher+"%')");
		 }
		 if(price!=null && !price.isEmpty()) {
			 Double pricevalue=Double.valueOf(price);
			 queryBuilder.append(" AND price ="+ pricevalue);
		 }
		 }
		  //list=booksservicedao.customQueryForSearch(queryBuilder);
		 
		 /* CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	        CriteriaQuery<BooksDetails> query = cb.createQuery(BooksDetails.class);
	        Root<BooksDetails> booksDetails = query.from(BooksDetails.class);
	        	Predicate categorypre=cb.like(booksDetails.get("category"), getContainsLikePattern(category));
	        	Predicate titlepre=cb.like(booksDetails.get("title"), getContainsLikePattern(title));
	        	Predicate authorpre=cb.like(booksDetails.get("author"), getContainsLikePattern(author));
	        	Predicate publisherpre=cb.like(booksDetails.get("publisher"), getContainsLikePattern(publisher));
	        	//Predicate pricepre=cb.like(booksDetails.get("price").as(String.class), getContainsLikePatternDouble(price));
	        	query.where(cb.or(categorypre,titlepre,authorpre,publisherpre));
	        	list=entityManager.createQuery(query).getResultList();*/
		  System.out.println(queryBuilder.toString());
		list=jdbctemplate.query(queryBuilder.toString(),new BooksDetailsMapper());
		return list;
	}
	private static String getContainsLikePatternDouble(Double searchTerm) {
		if (searchTerm == null ) {
            return "%";
        }
        else {
            return "%" + searchTerm + "%";
        }
	}
	private static String getContainsLikePattern(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return "%%";
        }
        else {
            return "%" + searchTerm.toLowerCase() + "%";
        }
    }
	
	@Override
	public BooksDetails getBookDetails(Long subscriptionId) {
		// TODO Auto-generated method stub
		return booksservicedao.findBySubscriptionId(subscriptionId);
	}
	@Override
	public String getBookContent(Long subscriptionId) {
		// TODO Auto-generated method stub
		BooksDetails book=booksservicedao.findBySubscriptionId(subscriptionId);;
		return book.getContents();
	}
	@Override
	public ResponseParms createBook(BooksDetails book) {
		// TODO Auto-generated method stub
		booksservicedao.save(book);
		ResponseParms params=new ResponseParms();
		params.setErrorcode(IBooksConstants.success_code.toString());
		params.setErrormessage(IBooksConstants.success_msg.toString());
		return params;
	}
	@Override
	public ResponseParms upadteBook(BooksDetails book) {
		BooksDetails dbBook=booksservicedao.findByBookId(book.getBookId());
		ResponseParms params=new ResponseParms();
		if(dbBook.getAuthorId().equals(book.getAuthorId())) {
		booksservicedao.save(book);
		params.setErrorcode(IBooksConstants.success_code.toString());
		params.setErrormessage(IBooksConstants.success_msg.toString());
		}else {
			params.setErrorcode(IBooksConstants.ERROR_code.toString());
			params.setErrormessage(IBooksConstants.EDIT_ERROR_msg.toString());
		}
		return params;
	}
	@Override
	public ResponseParms blockUnblockBook(Long authorid, Long bookid, String block) {
		BooksDetails dbBook=booksservicedao.findByBookId(bookid);
		ResponseParms params=new ResponseParms();
		if(dbBook.getAuthorId().equals(authorid)) {
			if("yes".equalsIgnoreCase(block)) {
				booksservicedao.upadteBlockBook(bookid,IBooksConstants.BLOCK_BOOK);
			}else {
				booksservicedao.upadteBlockBook(bookid,IBooksConstants.UN_BLOCK_BOOK);
			}
			params.setErrorcode(IBooksConstants.success_code.toString());
			params.setErrormessage(IBooksConstants.success_msg.toString());
		}else {
			params.setErrorcode(IBooksConstants.ERROR_code.toString());
			params.setErrormessage(IBooksConstants.EDIT_ERROR_msg.toString());
		}
		return params;
		
	}
}
