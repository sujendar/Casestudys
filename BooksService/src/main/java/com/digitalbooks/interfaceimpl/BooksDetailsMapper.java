package com.digitalbooks.interfaceimpl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.digitalbooks.entity.BooksDetails;

public class BooksDetailsMapper implements RowMapper<BooksDetails>{

	@Override
	public BooksDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		BooksDetails book=new BooksDetails();
		book.setBookId(rs.getLong("book_id"));
		book.setActive(rs.getInt("active"));
		book.setAuthor(rs.getString("author"));
		book.setCategory(rs.getString("category"));
		book.setContents(rs.getString("contents"));
		book.setLogo(rs.getString("logo"));
		book.setPrice(rs.getDouble("price"));
		book.setPublisheddate(rs.getDate("publisheddate"));
		book.setPublisher(rs.getString("publisher"));
		book.setTitle(rs.getString("title"));
		return book;
	}

}
